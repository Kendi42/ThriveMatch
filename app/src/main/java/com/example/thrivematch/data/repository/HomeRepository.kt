package com.example.thrivematch.data.repository

import android.util.Log
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.data.network.HomeDataAPI
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.network.networkBoundResource
import com.example.thrivematch.data.roomdb.database.AppDatabase
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class HomeRepository(
    private val api: HomeDataAPI,
    private val appDatabase: AppDatabase,
    private val commonSharedPreferences: CommonSharedPreferences

) : BaseRepository(){
    private var likedCardsList: MutableList<CardSwipeItemModel> = mutableListOf()

//    suspend fun getBusinessCardData(): Flow<Resource<List<CardSwipeItemModel>>> {
//        return networkBoundResource(
//            query = {
//                    appDatabase.swipeCardDao().getAllCards()
//
//            },
//            fetch = {
//                    api.getStartupCardData().startups.map { startup ->
//                        CardSwipeItemModel(
//                            cardID= startup.id,
//                            name = startup.name,
//                            industry = startup.industry,
//                            description = startup.description,
//                            imageURL = startup.picturePath
//                        )
//                    }
//            },
//            saveFetchResult = {cardSwipeItems->
//                appDatabase.swipeCardDao().insertCards(cardSwipeItems)
//                updateLastUpdateTime()
//            },
//            shouldFetch = {cachedCards->
//                val currentTime = System.currentTimeMillis()
//                Log.i("Current Time", currentTime.toString())
//                val lastUpdateTime = getLastUpdateTime() // Implement this function to get the last update time from SharedPreferences
//                Log.i("Last Updated Time", lastUpdateTime.toString())
//                val elapsedTimeMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTime - lastUpdateTime)
//                Log.i("Elapsed Time", elapsedTimeMinutes.toString())
//                val isStale = elapsedTimeMinutes >= Companion.UPDATE_INTERVAL_MINUTES
//                Log.i("isStale", isStale.toString())
//                isStale
//            }
//        )
//    }


    suspend fun getInvestorCardData(): Flow<Resource<List<CardSwipeItemModel>>> {
        return networkBoundResource(
            query = {
                appDatabase.swipeCardDao().getAllCards()

            },
            fetch = {
                api.getInvestorCardData().investors.map { investor ->
                    CardSwipeItemModel(
                        cardID=investor.id,
                        name = investor.name,
                        industry = investor.industry,
                        description = investor.description,
                        imageURL = investor.picturePath
                    )

                }
            },
            saveFetchResult = {cardSwipeItems->
                appDatabase.swipeCardDao().insertCards(cardSwipeItems)
                updateLastUpdateTime()
            },
            shouldFetch = {cachedCards->
                val currentTime = System.currentTimeMillis()
                Log.i("Current Time", currentTime.toString())
                val lastUpdateTime = getLastUpdateTime() // Implement this function to get the last update time from SharedPreferences
                Log.i("Last Updated Time", lastUpdateTime.toString())
                val elapsedTimeMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTime - lastUpdateTime)
                Log.i("Elapsed Time", elapsedTimeMinutes.toString())
                val isStale = elapsedTimeMinutes >= Companion.UPDATE_INTERVAL_MINUTES
                Log.i("isStale", isStale.toString())
                isStale
            }
        )
    }

    private fun updateLastUpdateTime() {
        val currentTime = System.currentTimeMillis()
        commonSharedPreferences.saveLongData(Constants.LAST_UPDATED_TIME, currentTime)
    }


    private fun getLastUpdateTime(): Long {
        return commonSharedPreferences.getLongData(Constants.LAST_UPDATED_TIME)
    }

    // Liking

    suspend fun saveLikedCard(savedCard: CardSwipeItemModel){
        val response= api.saveLikedCard(savedCard)
        Log.i("Save Response", response.toString())
    }


    suspend fun getLikedCards(): MutableList<PendingMatchModel>{
        Log.i("LikedCardsRepo", api.getLikedCards().toString())
       return api.getLikedCards()
    }


    companion object {
        const val UPDATE_INTERVAL_MINUTES= 1
    }


}

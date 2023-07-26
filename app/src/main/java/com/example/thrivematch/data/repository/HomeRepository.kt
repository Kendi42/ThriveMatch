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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.util.concurrent.TimeUnit

class HomeRepository(
    private val api: HomeDataAPI,
    private val appDatabase: AppDatabase,
    private val commonSharedPreferences: CommonSharedPreferences

) : BaseRepository(){
    private var likedCardsList: MutableList<CardSwipeItemModel> = mutableListOf()


    suspend fun getCardData(): Flow<Resource<List<CardSwipeItemModel>>> {
        return withContext(Dispatchers.IO){
            val individualInvestor= appDatabase.userDao().getCurrentUser()!!.hasCreatedIndividualInvestor
            val investor= appDatabase.userDao().getCurrentUser()!!.hasCreatedInvestor
            val startup= appDatabase.userDao().getCurrentUser()!!.hasCreatedStartUp
        networkBoundResource(
            query = {
                    appDatabase.swipeCardDao().getAllCards()

            },
            fetch = {
                    if (individualInvestor || investor) {
                        Log.i("Investor trueeee","Investor trueeee" )
                        api.getStartupCardData().startups.map { startup ->
                            CardSwipeItemModel(
                                cardID = startup.id,
                                name = startup.name,
                                industry = startup.industry,
                                description = startup.description,
                                imageURL = startup.picturePath
                            )
                        }
                    } else if (startup) {
                        Log.i("Startup trueeee","Startup trueeee" )
                        api.getInvestorCardData().investors.map { investor ->
                            CardSwipeItemModel(
                                cardID = investor.id,
                                name = investor.name,
                                industry = investor.industry,
                                description = investor.description,
                                imageURL = investor.picturePath
                            )
                        }
                    } else {
                        Log.i("Neither trueeee","Neither trueeee" )
                        emptyList()
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
        ).flowOn(Dispatchers.Main)
    }}

     private fun updateLastUpdateTime() {
        val currentTime = System.currentTimeMillis()
        commonSharedPreferences.saveLongData(Constants.LAST_UPDATED_TIME, currentTime)
    }


     private fun getLastUpdateTime(): Long {
        return commonSharedPreferences.getLongData(Constants.LAST_UPDATED_TIME)
    }

    // Liking

     suspend fun saveLikedCard(savedCard: CardSwipeItemModel){
         Log.i("In liked repo", "In liked repo")
         val response= api.saveLikedCard(savedCard)
        Log.i("Save Response", response.toString())
    }


     suspend fun getLikedCards(): MutableList<PendingMatchModel>{
        Log.i("LikedCardsRepo", api.getLikedCards().toString())
       return api.getLikedCards()
    }

    suspend fun getMatchedCards(): MutableList<PendingMatchModel> {
        return api.getMatchedCards()

    }

    companion object {
        const val UPDATE_INTERVAL_MINUTES= 1
    }


}

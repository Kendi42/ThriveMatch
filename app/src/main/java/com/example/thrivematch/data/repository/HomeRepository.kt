package com.example.thrivematch.data.repository

import android.util.Log
import android.widget.Toast
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.models.MatchedModel
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.data.network.HomeDataAPI
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.network.networkBoundResource
import com.example.thrivematch.data.roomdb.database.AppDatabase
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import com.example.thrivematch.util.Constants.USER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.util.concurrent.TimeUnit

class HomeRepository(
    private val api: HomeDataAPI,
    private val appDatabase: AppDatabase,
    private val commonSharedPreferences: CommonSharedPreferences

) : BaseRepository(){
    private var likedCardsList: MutableList<CardSwipeItemModel> = mutableListOf()

    private var individualInvestor: Boolean = false
    private var investor: Boolean = false
    private var startup: Boolean = false





    suspend fun getCardData(): Flow<Resource<List<CardSwipeItemModel>>> {
        return withContext(Dispatchers.IO){
            individualInvestor= appDatabase.userDao().getCurrentUser()!!.hasCreatedIndividualInvestor
            investor= appDatabase.userDao().getCurrentUser()!!.hasCreatedInvestor
            startup= appDatabase.userDao().getCurrentUser()!!.hasCreatedStartUp
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

     suspend fun saveLikedCard(savedCard: CardSwipeItemModel) {
         Log.i("In liked repo", "In liked repo")
//         val response= api.saveLikedCard(savedCard)
//        Log.i("Save Response", response.toString())
         try{
         withContext(Dispatchers.IO) {
             individualInvestor = appDatabase.userDao().getCurrentUser()!!.hasCreatedIndividualInvestor
             investor = appDatabase.userDao().getCurrentUser()!!.hasCreatedInvestor
             startup = appDatabase.userDao().getCurrentUser()!!.hasCreatedStartUp
             val investorData = api.getInvestorDetails()
             val startupData = api.getStartupDetails()
             if(individualInvestor || investor){
                 val matchingInvestor = investorData.investorDetailsList.find { investor ->
                     investor.userId == USER_ID
                 }
                 if(matchingInvestor != null){
                     val investorID = matchingInvestor.id
                     val startupID = savedCard.cardID

                     launch(Dispatchers.IO) {
                         api.investorLikeStartup(startupID = startupID, investorID = investorID)
                     }                 }

             }else if(startup){
                 val matchingStartup = startupData.startUpDetailsList.find { startup ->
                     startup.userId == USER_ID
                 }
                 if(matchingStartup != null){
                     val startupID = matchingStartup.id
                     val investorID = savedCard.cardID

                     launch(Dispatchers.IO) {
                         api.startupLikeInvestor(startupID = startupID, investorID = investorID)
                     }                 }


             }

         }}
         catch (e: Exception) {
             // Handle any exceptions that may occur during the API calls
             e.printStackTrace()
             Log.i("Error", e.toString())
         }
         Log.i("End of liked repo", "End of liked repo")

     }


     suspend fun getLikedCards(): MutableList<PendingMatchModel>{
         var returnValue = mutableListOf<PendingMatchModel>()
         withContext(Dispatchers.IO){
             individualInvestor= appDatabase.userDao().getCurrentUser()!!.hasCreatedIndividualInvestor
             investor= appDatabase.userDao().getCurrentUser()!!.hasCreatedInvestor
             startup= appDatabase.userDao().getCurrentUser()!!.hasCreatedStartUp

             returnValue = if (individualInvestor || investor) {
                 api.getLikedCards()

             } else if (startup) {
                 api.getLikedInvestorCards()
             } else{
                 returnValue
             }
         }
         return returnValue

    }

    suspend fun getMatchedCards(): MutableList<MatchedModel> {
        var returnValue = mutableListOf<MatchedModel>()
        withContext(Dispatchers.IO){
            individualInvestor= appDatabase.userDao().getCurrentUser()!!.hasCreatedIndividualInvestor
            investor= appDatabase.userDao().getCurrentUser()!!.hasCreatedInvestor
            startup= appDatabase.userDao().getCurrentUser()!!.hasCreatedStartUp

            returnValue = if (individualInvestor || investor) {
                api.getMatchedCards()

            } else if (startup) {
                api.getMatchedInvestorCards()
            } else{
                returnValue
            }
        }
        return returnValue
    }

    companion object {
        const val UPDATE_INTERVAL_MINUTES= 1
    }


}

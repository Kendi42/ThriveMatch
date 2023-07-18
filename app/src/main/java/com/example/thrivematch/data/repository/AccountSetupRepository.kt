package com.example.thrivematch.data.repository

import android.util.Log
import com.example.thrivematch.data.models.BusinessModel
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.network.AccountSetupAPI
import com.example.thrivematch.data.network.AuthAPI
import com.example.thrivematch.data.request.LoginRequest
import com.example.thrivematch.data.request.SignupRequest
import com.example.thrivematch.data.response.User
import com.example.thrivematch.data.roomdb.database.AppDatabase

class AccountSetupRepository(
    private val api: AccountSetupAPI,
    private val appDatabase: AppDatabase
): BaseRepository() {

    suspend fun investorSetup(investorData: InvestorModel ) = safeApiCall{
        val apiResponse = api.investorAccountSetup(InvestorModel(investorType = investorData.investorType, name=investorData.name, description = investorData.description, selectedInterests = investorData.selectedInterests, photo = investorData.photo))
        if(apiResponse.success){
            var currentUser = appDatabase.userDao().getCurrentUser()
            if (currentUser != null && currentUser.setupData?.success != true) {
                Log.i("Conditions Met", "If Conditions met")
                currentUser.setupData = apiResponse
                Log.i("Current User Updated", currentUser.toString())
                appDatabase.userDao().updateUser(currentUser)
                Log.i("After DB update", "After DB update")
                appDatabase.accountSetupDao().insertInvestorAccountData(investorData)
            }

        }
        apiResponse
    }

    suspend fun businessSetup (businessData: BusinessModel) = safeApiCall{
        val apiResponse= api.businessAccountSetup(BusinessModel(businessName = businessData.businessName, industry = businessData.industry, dateFounded = businessData.dateFounded, companyDescription = businessData.companyDescription, phoneNumber = businessData.phoneNumber,
                                                email = businessData.email, address=businessData.address, poBox = businessData.poBox, photo = businessData.photo))
        if(apiResponse.success){
            var currentUser = appDatabase.userDao().getCurrentUser()
            if (currentUser != null && currentUser.setupData?.success != true) {
                Log.i("Conditions Met", "If Conditions met")
                currentUser.setupData = apiResponse
                Log.i("Current User Updated", currentUser.toString())
                appDatabase.userDao().updateUser(currentUser)
                Log.i("After DB update", "After DB update")
                appDatabase.accountSetupDao().insertBusinessAccountData(businessData)
            }
        }
        apiResponse

    }
}
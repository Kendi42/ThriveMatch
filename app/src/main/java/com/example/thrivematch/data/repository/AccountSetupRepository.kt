package com.example.thrivematch.data.repository

import android.util.Log
import com.example.thrivematch.data.models.BusinessModel
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.network.AccountSetupAPI
import com.example.thrivematch.data.roomdb.database.AppDatabase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

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
        var imagePart: MultipartBody.Part? = null
        val imageFile = File(businessData.photo)
        if (imageFile.exists()) {
            val reqFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
             imagePart = MultipartBody.Part.createFormData("image", imageFile.name, reqFile)
        }
        val apiResponse = api.businessAccountSetup(
            image = imagePart,
            businessName = businessData.businessName.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            industry = businessData.industry.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            dateFounded = businessData.dateFounded.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            companyDescription = businessData.companyDescription.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
//            phoneNumber = businessData.phoneNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            email = businessData.email.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            address=businessData.address.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            poBox = businessData.poBox.toRequestBody("multipart/form-data".toMediaTypeOrNull()))
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
        else{
            Log.e("Failed Upload", apiResponse.toString())
        }
        apiResponse
    }
}
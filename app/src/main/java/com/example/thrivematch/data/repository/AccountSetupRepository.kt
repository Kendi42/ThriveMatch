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
        var currentUser = appDatabase.userDao().getCurrentUser()
        var imagePart: MultipartBody.Part? = null
        val imageFile = File(investorData.photo)
        if (imageFile.exists()) {
            val reqFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            imagePart = MultipartBody.Part.createFormData("image", imageFile.name, reqFile)
        }
        val userEmail= currentUser?.email
        val apiResponse = api.investorAccountSetup(
            image = imagePart,
            investorName = investorData.name.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            industry = investorData.selectedInterests.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            description = investorData.description.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            address= "Nairobi".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            email = userEmail?.toRequestBody("multipart/form-data".toMediaTypeOrNull())!!
        )

        if(apiResponse.success){
            if (currentUser != null && currentUser.hasCreatedIndividualInvestor != true) {
                Log.i("Conditions Met", "If Conditions met")
                currentUser.hasCreatedIndividualInvestor = apiResponse.success
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
            if (currentUser != null && currentUser.hasCreatedStartUp != true) {
                Log.i("Conditions Met", "If Conditions met")
                currentUser.hasCreatedStartUp = apiResponse.success
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
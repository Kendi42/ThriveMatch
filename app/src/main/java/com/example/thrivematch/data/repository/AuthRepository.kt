package com.example.thrivematch.data.repository

import android.util.Log
import android.widget.Toast
import com.example.thrivematch.data.network.AuthAPI
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.request.LoginRequest
import com.example.thrivematch.data.request.SignupRequest
import com.example.thrivematch.data.response.LoginResponse
import com.example.thrivematch.data.response.User
import com.example.thrivematch.data.roomdb.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AuthRepository(
    private val api:AuthAPI,
    private val appDatabase: AppDatabase
    ) : BaseRepository() {
    suspend fun login(email: String, password: String): Resource<LoginResponse> = safeApiCall{
        var apiResponse= api.login(LoginRequest(email = email, password = password))
        if(apiResponse.success){
            Log.i("Success true", apiResponse.success.toString())
            var currentUser = appDatabase.userDao().getCurrentUser()
            Log.i("Checking current user", currentUser.toString())
            if(currentUser==null || currentUser.email != apiResponse.user.email){
                Log.i("creating new user", "Creating new user")
                appDatabase.clearAllTables()
                appDatabase.userDao().createNewUser(apiResponse.user)
            }
            else{
                if(currentUser.id == null){
                    currentUser.id = apiResponse.user.id
                    appDatabase.userDao().updateUser(currentUser)
            }
                currentUser.hasCreatedInvestor = apiResponse.user.hasCreatedInvestor
                currentUser.hasCreatedIndividualInvestor = apiResponse.user.hasCreatedIndividualInvestor
                currentUser.hasCreatedStartUp = apiResponse.user.hasCreatedStartUp
                appDatabase.userDao().updateUser(currentUser)
            }
        }
        apiResponse
    }

    suspend fun signup (name: String, email: String, password: String) = safeApiCall{
        val apiResponse= api.signup(SignupRequest(username =name, password = password, email =  email))
        if(apiResponse.success){
            Log.i("Success true", apiResponse.success.toString())
            val currentUser = appDatabase.userDao().getCurrentUser()
            Log.i("Checking current user", currentUser.toString())
            if(currentUser==null || currentUser.email != email){
                Log.i("creating new user", "Creating new user")
                appDatabase.clearAllTables()
                appDatabase.userDao().createNewUser(User(email=email, username = name, id = null, hasCreatedIndividualInvestor = false, hasCreatedInvestor = false, hasCreatedStartUp = false))
            }
        }
        apiResponse
    }
    suspend fun logout(){
        api.logout()
    }


}
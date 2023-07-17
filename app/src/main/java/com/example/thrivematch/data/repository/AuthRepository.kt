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
        val apiResponse= api.login(LoginRequest(email = email, password = password))
        if(apiResponse.success){
            val currentUser = appDatabase.userDao().getCurrentUser()
            Log.i("Checking current user", currentUser.toString())
            if(currentUser==null ||currentUser.id != apiResponse.user.id ){
                Log.i("creating new user", "Creating new user")
                appDatabase.userDao().createNewUser(User(email="kendi.anyika@gmail.com", username = "Kendi Anyika", id = 8, setupData = null))
            }
        }
        apiResponse
    }

    suspend fun signup (name: String, email: String, password: String) = safeApiCall{
        api.signup(SignupRequest(username =name, password = password, email =  email))
    }

}
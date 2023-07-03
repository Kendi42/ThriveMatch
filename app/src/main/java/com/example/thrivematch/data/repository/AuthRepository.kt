package com.example.thrivematch.data.repository

import android.util.Log
import com.example.thrivematch.data.network.AuthAPI
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AuthRepository(
    private val api:AuthAPI
    ): BaseRepository() {

    suspend fun login(email: String, password: String) = safeApiCall{
        api.login(LoginRequest(email = email, password = password))
    }

    suspend fun signup (name: String, email: String, password: String) {
    }

}
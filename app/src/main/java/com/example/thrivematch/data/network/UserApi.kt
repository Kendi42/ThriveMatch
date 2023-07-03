package com.example.thrivematch.data.network

import com.example.thrivematch.data.response.LoginResponse
import retrofit2.http.GET

interface UserApi {
    @GET("user")
    suspend fun getUserData(): LoginResponse
}
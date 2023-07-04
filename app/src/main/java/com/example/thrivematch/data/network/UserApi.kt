package com.example.thrivematch.data.network

import com.example.thrivematch.data.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @GET("user")
    suspend fun getUserData(): LoginResponse

    @POST("logout")
    suspend fun logout():ResponseBody
}
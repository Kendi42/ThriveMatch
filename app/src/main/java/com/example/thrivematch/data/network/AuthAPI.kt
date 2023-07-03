package com.example.thrivematch.data.network

import com.example.thrivematch.data.request.LoginRequest
import com.example.thrivematch.data.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("v1/userLogin")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

}
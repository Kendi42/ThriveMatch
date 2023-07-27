package com.example.thrivematch.data.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.thrivematch.data.request.LoginRequest
import com.example.thrivematch.data.request.SignupRequest
import com.example.thrivematch.data.response.LoginResponse
import com.example.thrivematch.data.response.SignupResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("v1/userLogin")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse


    @POST("v1/userRegister")
    suspend fun signup(
        @Body signupRequest: SignupRequest
    ): SignupResponse
    
    @POST("v1/logout")
    suspend fun logout(): ResponseBody

}
package com.example.thrivematch.data.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.thrivematch.data.request.LoginRequest
import com.example.thrivematch.data.request.SignupRequest
import com.example.thrivematch.data.response.LoginResponse
import com.example.thrivematch.data.response.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @Mock
    @MockResponse(body = "{\"message\":\"login successful !!\",\"success\":true,\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTBAZ21haWwuY29tIiwiaWF0IjoxNjg4MTMxNDg1LCJleHAiOjE2ODgyMTc4ODUsInVzZXJ0eXBlIjoiVVNFUiJ9.dUkbN3iZdhRablbnRC6DA16alEX6Lhvwx4m_d9rbETE\",\"user\":{\"email\":\"user10@gmail.com\",\"id\":8,\"username\":\"user10\"}}")
    @POST("v1/userLogin")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @Mock
    @MockResponse(body = "{\"message\":\"User Created Successfully !!\",\"success\":true}")
    @POST("v1/userRegister")
    suspend fun signup(
        @Body signupRequest: SignupRequest
    ): SignupResponse

//    @POST("v1/userLogin")
//    suspend fun login(
//        @Body loginRequest: LoginRequest
//    ): LoginResponse
//
//
//    @POST("v1/userRegister")
//    suspend fun signup(
//        @Body signupRequest: SignupRequest
//    ): SignupResponse

}
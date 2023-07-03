package com.example.thrivematch.data.request


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String, // user10@gmail.com
    @SerializedName("password")
    val password: String // user10
)
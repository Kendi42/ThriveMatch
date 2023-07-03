package com.example.thrivematch.data.request


import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("email")
    val email: String, // user30@gmail.com
    @SerializedName("password")
    val password: String, // user30
    @SerializedName("username")
    val username: String // user30
)
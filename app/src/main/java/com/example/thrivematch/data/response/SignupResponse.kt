package com.example.thrivematch.data.response


import androidx.room.Entity
import com.google.gson.annotations.SerializedName


data class SignupResponse(
    @SerializedName("message")
    val message: String, // User Created Successfully !!
    @SerializedName("success")
    val success: Boolean // true
)
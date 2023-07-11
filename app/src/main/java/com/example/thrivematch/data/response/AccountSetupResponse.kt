package com.example.thrivematch.data.response

import com.google.gson.annotations.SerializedName

data class AccountSetupResponse(
@SerializedName("message")
val message: String, // Account Created Successfully !!
@SerializedName("success")
val success: Boolean // true
)

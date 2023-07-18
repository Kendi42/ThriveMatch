package com.example.thrivematch.data.response

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class AccountSetupResponse(
@SerializedName("message")
var message: String, // Account Created Successfully !!
@SerializedName("success")
var success: Boolean, // true
)




package com.example.thrivematch.data.models

import com.google.gson.annotations.SerializedName

data class PendingMatchModel(
    @SerializedName("picturePath")
    val imageURL: String,
    @SerializedName("name")
    val name: String
)

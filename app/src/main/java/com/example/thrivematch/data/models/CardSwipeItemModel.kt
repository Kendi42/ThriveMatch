package com.example.thrivematch.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
//
@Entity(tableName = "CardData")
data class CardSwipeItemModel(
    @PrimaryKey(autoGenerate = true)
    val cardID: Int = 0,
    @SerializedName("name")
    val name: String,
    @SerializedName("industry")
    val industry: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("imageURL")
    val imageURL: String
)

//data class CardSwipeItemModel(
////    val id: String,
//    val name: String,
//    val industry: String,
//    val description: String,
//    val imageURL: String
//)
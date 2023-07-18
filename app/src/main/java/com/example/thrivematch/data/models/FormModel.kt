package com.example.thrivematch.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.thrivematch.data.roomdb.Converts
import com.example.thrivematch.data.roomdb.dao.ROOM_DB_CURRENT_USER_ID
import com.google.gson.annotations.SerializedName


@Entity(tableName = "InvestorAccountData")
data class InvestorModel(
    @PrimaryKey(autoGenerate = false)
    var id: Int = ROOM_DB_CURRENT_USER_ID,
    @SerializedName("investorType")
    var investorType: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("description")
    var description: String,
    @TypeConverters(Converts::class)
    @SerializedName("selectedInterests")
    var selectedInterests: MutableList<String>?  = null,
    @SerializedName("photo")
    var photo: String?
)

@Entity(tableName = "BusinessAccountData")
data class BusinessModel(
    @PrimaryKey(autoGenerate = false)
    var id: Int = ROOM_DB_CURRENT_USER_ID,
    @SerializedName("businessName")
    var businessName: String,
    @SerializedName("industry")
    var industry: String,
    @SerializedName("dateFounded")
    var dateFounded: String,
    @SerializedName("companyDescription")
    var companyDescription: String,
    @SerializedName("phoneNumber")
    var phoneNumber: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("address")
    var address: String,
    @SerializedName("poBox")
    var poBox: String,
    @SerializedName("photo")
    var photo: String?
)



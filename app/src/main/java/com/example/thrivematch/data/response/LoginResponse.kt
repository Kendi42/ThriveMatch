package com.example.thrivematch.data.response


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thrivematch.data.roomdb.dao.ROOM_DB_CURRENT_USER_ID
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String, // login successful !!
    @SerializedName("success")
    val success: Boolean, // true
    @SerializedName("token")
    val token: String, // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrZW5kaS5hbnlpa2FAZ21haWwuY29tIiwiaWF0IjoxNjkwMjg4NzAzLCJleHAiOjE2OTAzMTAzMDMsInVzZXJ0eXBlIjoiVVNFUiJ9.QypPshQHqlqYON53785ztybBunChQtqgIHkDicfpRVw
    @SerializedName("user")
    val user: User
)

@Entity(tableName = "UserData")
data class User(
        @SerializedName("email")
        val email: String, // kendi.anyika@gmail.com
        @SerializedName("hasCreatedIndividualInvestor")
        var hasCreatedIndividualInvestor: Boolean, // false
        @SerializedName("hasCreatedInvestor")
        var hasCreatedInvestor: Boolean, // false
        @SerializedName("hasCreatedStartUp")
        var hasCreatedStartUp: Boolean, // false
        @SerializedName("id")
        var id: Int?, // 26
        @SerializedName("username")
        val username: String // Kendi Anyika
    ){
    @PrimaryKey(autoGenerate = false)
    var uid = ROOM_DB_CURRENT_USER_ID
}

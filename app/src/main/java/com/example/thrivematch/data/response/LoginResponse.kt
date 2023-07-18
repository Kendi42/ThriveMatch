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
    val token: String, // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTBAZ21haWwuY29tIiwiaWF0IjoxNjg4MTMxNDg1LCJleHAiOjE2ODgyMTc4ODUsInVzZXJ0eXBlIjoiVVNFUiJ9.dUkbN3iZdhRablbnRC6DA16alEX6Lhvwx4m_d9rbETE
    @SerializedName("user")
    var user: User
)

@Entity(tableName = "UserData")
data class User(
        @SerializedName("email")
        val email: String, // user10@gmail.com
        @SerializedName("id")
        var id: Int?, // 8
        @SerializedName("username")
        val username: String,// user10
        @SerializedName("setup")
        @Embedded(prefix = "setupdata_")
        val setupData: AccountSetupResponse?
    ){
    @PrimaryKey(autoGenerate = false)
    var uid = ROOM_DB_CURRENT_USER_ID
}

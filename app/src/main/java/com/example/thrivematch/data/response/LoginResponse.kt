package com.example.thrivematch.data.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String, // login successful !!
    @SerializedName("success")
    val success: Boolean, // true
    @SerializedName("token")
    val token: String, // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTBAZ21haWwuY29tIiwiaWF0IjoxNjg4MTMxNDg1LCJleHAiOjE2ODgyMTc4ODUsInVzZXJ0eXBlIjoiVVNFUiJ9.dUkbN3iZdhRablbnRC6DA16alEX6Lhvwx4m_d9rbETE
    @SerializedName("user")
    val user: User
) {
    data class User(
        @SerializedName("email")
        val email: String, // user10@gmail.com
        @SerializedName("id")
        val id: Int, // 8
        @SerializedName("username")
        val username: String // user10
    )
}
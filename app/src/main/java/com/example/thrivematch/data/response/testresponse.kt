package com.example.thrivematch.data.response


import com.google.gson.annotations.SerializedName

data class testresponse(
    @SerializedName("message")
    val message: String, // login successful !!
    @SerializedName("success")
    val success: Boolean, // true
    @SerializedName("token")
    val token: String, // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrZW5kaS5hbnlpa2FAZ21haWwuY29tIiwiaWF0IjoxNjkwMjg4NzAzLCJleHAiOjE2OTAzMTAzMDMsInVzZXJ0eXBlIjoiVVNFUiJ9.QypPshQHqlqYON53785ztybBunChQtqgIHkDicfpRVw
    @SerializedName("user")
    val user: User
) {
    data class User(
        @SerializedName("email")
        val email: String, // kendi.anyika@gmail.com
        @SerializedName("hasCreatedIndividualInvestor")
        val hasCreatedIndividualInvestor: Boolean, // false
        @SerializedName("hasCreatedInvestor")
        val hasCreatedInvestor: Boolean, // false
        @SerializedName("hasCreatedStartUp")
        val hasCreatedStartUp: Boolean, // false
        @SerializedName("id")
        val id: Int, // 26
        @SerializedName("username")
        val username: String // Kendi Anyika
    )
}
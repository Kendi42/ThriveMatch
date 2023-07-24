package com.example.thrivematch.data.response


import com.google.gson.annotations.SerializedName

data class InvestorResponseData(
    @SerializedName("investors")
    val investors: List<Investor>,
    @SerializedName("message")
    val message: String, // All investors
    @SerializedName("success")
    val success: Boolean // true
) {
    data class Investor(
        @SerializedName("description")
        val description: String, // Posh investoe
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("industry")
        val industry: String, // Any field
        @SerializedName("name")
        val name: String, // Investor3
        @SerializedName("picturePath")
        val picturePath: String // /home/johnmalek/Desktop/Malek/SpringBoot/ThriveMatch/src/main/java/com/thrivematch/ThriveMatch/files/twitter.png
    )
}
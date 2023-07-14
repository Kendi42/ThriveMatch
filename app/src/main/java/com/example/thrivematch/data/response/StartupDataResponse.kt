package com.example.thrivematch.data.response


import com.google.gson.annotations.SerializedName


data class StartupDataResponse(
    @SerializedName("message")
    val message: String, // All startups
    @SerializedName("startups")
    val startups: List<Startup>,
    @SerializedName("success")
    val success: Boolean // true
) {
    data class Startup(
        @SerializedName("description")
        val description: String, // I would really like to be funded
        @SerializedName("industry")
        val industry: String, // Telecommunication
        @SerializedName("name")
        val name: String, // StartupA
        @SerializedName("picturePath")
        val picturePath: String // /home/johnmalek/Desktop/Malek/SpringBoot/ThriveMatch/src/main/java/com/thrivematch/ThriveMatch/files/twitter.png
    )
}
package com.example.thrivematch.data.response


import com.google.gson.annotations.SerializedName

data class AllStartupDetails(
    @SerializedName("message")
    val message: String, // All startups
    @SerializedName("startUpDetailsList")
    val startUpDetailsList: List<StartUpDetails>,
    @SerializedName("success")
    val success: Boolean // true
) {
    data class StartUpDetails(
        @SerializedName("address")
        val address: String, // Kampala
        @SerializedName("admin_id")
        val adminId: Int, // 3
        @SerializedName("dateCreated")
        val dateCreated: String, // 2023-07-25
        @SerializedName("description")
        val description: String, // This is a startup for the new user that just signed up!
        @SerializedName("email")
        val email: String, // newuserstartup@gmail.com
        @SerializedName("id")
        val id: Int, // 8
        @SerializedName("industry")
        val industry: String, // Technology
        @SerializedName("name")
        val name: String, // NewUserStartup
        @SerializedName("picturePath")
        val picturePath: String, // http://res.cloudinary.com/dkkxufuqp/image/upload/v1690287533/iyz84kmnusr90g3qinuq.jpg
        @SerializedName("poBox")
        val poBox: String, // 9843
        @SerializedName("user_id")
        val userId: Int, // 21
        @SerializedName("yearFounded")
        val yearFounded: String // 2022-09-23
    )
}
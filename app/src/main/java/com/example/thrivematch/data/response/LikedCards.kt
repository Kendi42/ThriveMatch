package com.example.thrivematch.data.response


import com.google.gson.annotations.SerializedName

    data class LikedCardsItem(
        @SerializedName("name")
        val name: String, // Mike
        @SerializedName("picturePath")
        val picturePath: String // http://res.cloudinary.com/dkkxufuqp/image/upload/v1690355602/fn33louk6zeazlfop6pm.png
    )

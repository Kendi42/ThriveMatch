package com.example.thrivematch.data.response


import com.google.gson.annotations.SerializedName

data class AllInvestorDetails(
    @SerializedName("investorDetailsList")
    val investorDetailsList: List<InvestorDetails>,
    @SerializedName("message")
    val message: String, // All investors
    @SerializedName("success")
    val success: Boolean // true
) {
    data class InvestorDetails(
        @SerializedName("address")
        val address: String, // adminHQ
        @SerializedName("admin_id")
        val adminId: Int, // 3
        @SerializedName("dateCreated")
        val dateCreated: String, // 2023-07-25
        @SerializedName("description")
        val description: String, // This investor was created by the admin
        @SerializedName("email")
        val email: String, // admininvestor@gmail.com
        @SerializedName("id")
        val id: Int, // 6
        @SerializedName("industry")
        val industry: String, // admin services
        @SerializedName("name")
        val name: String, // adminInvestor
        @SerializedName("picturePath")
        val picturePath: String, // http://res.cloudinary.com/dkkxufuqp/image/upload/v1690310041/q5qnnqfbjfaumck0dkab.jpg
        @SerializedName("poBox")
        val poBox: String, // 8659
        @SerializedName("user_id")
        val userId: Int, // 21
        @SerializedName("yearFounded")
        val yearFounded: String // 2023-05-24
    )
}
package com.example.thrivematch.data.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.response.AccountSetupResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AccountSetupAPI {


    @Multipart
    @POST("v1/individual_investor")
    suspend fun investorAccountSetup(
        @Part image: MultipartBody.Part?,
        @Part("name") investorName: RequestBody,
        @Part("industry") industry: RequestBody,
        @Part("desc") description: RequestBody,
        @Part("address") address: RequestBody,
        @Part("email") email: RequestBody,
    ): AccountSetupResponse

    @Multipart
    @POST("v1/add_startup")
    suspend fun businessAccountSetup(
        @Part image: MultipartBody.Part?,
        @Part("name") businessName: RequestBody,
        @Part("industry") industry: RequestBody,
        @Part("year") dateFounded: RequestBody,
        @Part("desc") companyDescription: RequestBody,
        @Part("email") email: RequestBody,
        @Part("address") address: RequestBody,
        @Part("poBox") poBox: RequestBody
    ): AccountSetupResponse




}
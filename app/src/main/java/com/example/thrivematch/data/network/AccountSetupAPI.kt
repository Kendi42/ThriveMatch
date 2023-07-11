package com.example.thrivematch.data.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockHeader
import co.infinum.retromock.meta.MockResponse
import co.infinum.retromock.meta.MockResponses
import com.example.thrivematch.data.models.BusinessModel
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.response.AccountSetupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountSetupAPI {

    @Mock
    @MockResponse(body ="{\"message\":\"Account Setup Successfully !!\",\"success\":true}" )
    @POST("v1/investorAccountSetup")
    suspend fun investorAccountSetup(
        @Body accountSetupRequest: InvestorModel
    ): AccountSetupResponse

    @Mock
    @MockResponse(body ="{\"message\":\"Account Setup Successfully !!\",\"success\":true}" )

    @POST("v1/businessAccountSetup")
    suspend fun businessAccountSetup(
        @Body accountSetupRequest: BusinessModel
    ): AccountSetupResponse

}
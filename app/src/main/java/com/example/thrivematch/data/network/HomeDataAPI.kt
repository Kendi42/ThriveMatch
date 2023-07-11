package com.example.thrivematch.data.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.response.AccountSetupResponse
import com.example.thrivematch.data.response.StartupDataResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeDataAPI {
    @Mock
    @MockResponse(body =
    "{\"message\":\"Account Setup Successfully !!\"," +
            "\"startups\":[{\"description\":\"Harnessing the limitless potential of the sun, we're empowering individuals and businesses with clean, reliable, and sustainable energy solutions.\",\"industry\":\"Sustainable Energy\",\"name\":\"Bloom Energy\",\"picturePath\":\"https://img.freepik.com/free-vector/green-alternative-energy-power-logo_126523-2775.jpg?size=626&ext=jpg&ga=GA1.2.1090819380.1686834206&semt=ais\"}," +
            "]," +
            "\"success\":true}")
    @POST("v1/getStartupData")
    suspend fun getStartupCardData(
    ): StartupDataResponse
}
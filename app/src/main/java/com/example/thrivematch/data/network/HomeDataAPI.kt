package com.example.thrivematch.data.network

import android.util.Log
import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockBehavior
import co.infinum.retromock.meta.MockResponse
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.models.MatchedModel
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.data.request.LoginRequest
import com.example.thrivematch.data.request.SignupRequest
import com.example.thrivematch.data.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface HomeDataAPI {
    // Real API responses

    @GET("v1/all_startups")
    suspend fun getStartupCardData(
    ): StartupDataResponse

//
    @GET("v1/all_investors")
    suspend fun getInvestorCardData(
    ): InvestorResponseData



    // Mock responses
    @Mock
    @MockResponse(body = "{\"message\":\"Successfully Saved !!\",\"success\":true}")
    @POST("v1/saveLikedCard")
    suspend fun saveLikedCard(
        @Body savedCard: CardSwipeItemModel
    ): SignupResponse

    @Mock
    @POST("v1/getLikedCards")
    @MockResponse(body = "[{\"imageURL\":\"https://img.freepik.com/free-vector/green-alternative-energy-power-logo_126523-2775.jpg?size=626&ext=jpg&ga=GA1.2.1090819380.1686834206&semt=ais\",\"name\":\"Bloom Energy\"}," +
            "{\"imageURL\":\"https://cdn.dribbble.com/userupload/7733577/file/original-a2f0a453abc9ef61612d721aeb8a39da.jpg?compress=1&resize=2048x1536\",\"name\":\"BTech\"}]")
    @MockBehavior(durationMillis = 0, durationDeviation = 0)
    suspend fun getLikedCards(): MutableList<PendingMatchModel>

    @Mock
    @POST("v1/getLikedCards")
    @MockResponse(body = "[{\"imageURL\":\"https://images.pexels.com/photos/3769021/pexels-photo-3769021.jpeg?auto=compress&cs=tinysrgb&w=600\",\"name\":\"Madeline Mugo\"}," +
            "{\"imageURL\":\"https://images.pexels.com/photos/4173168/pexels-photo-4173168.jpeg?auto=compress&cs=tinysrgb&w=600\",\"name\":\"Amani Onyango\"}]")
    @MockBehavior(durationMillis = 0, durationDeviation = 0)
    suspend fun getLikedInvestorCards(): MutableList<PendingMatchModel>


    @Mock
    @POST("v1/getLikedCards")
    @MockResponse(body = "[{\"imageURL\":\"https://cdn.dribbble.com/userupload/7889038/file/original-8a3114ac067714ed900bb8437175ec7c.jpg?compress=1&resize=1504x1128\",\"name\":\"ArrowHealth\",\"phoneNumber\":\"254702766735\"}," +
            "{\"imageURL\":\"https://cdn.dribbble.com/userupload/8098458/file/original-a2252dbb9fbd1b6fc989cea4156f9519.jpg?compress=1&resize=1338x1003&vertical=center\",\"name\":\"Drivable\",\"phoneNumber\":\"254111690030\"}]")
    @MockBehavior(durationMillis = 0, durationDeviation = 0)
    suspend fun getMatchedCards(): MutableList<MatchedModel>


    @Mock
    @POST("v1/getLikedCards")
    @MockResponse(body = "[{\"imageURL\":\"https://images.pexels.com/photos/3769021/pexels-photo-3769021.jpeg?auto=compress&cs=tinysrgb&w=600\",\"name\":\"Madeline Mugo\",\"phoneNumber\":\"254702766735\"}," +
            "{\"imageURL\":\"https://images.pexels.com/photos/4173168/pexels-photo-4173168.jpeg?auto=compress&cs=tinysrgb&w=600\",\"name\":\"Amani Onyango\",\"phoneNumber\":\"254111690030\"}]")
    @MockBehavior(durationMillis = 0, durationDeviation = 0)
    suspend fun getMatchedInvestorCards(): MutableList<MatchedModel>

}
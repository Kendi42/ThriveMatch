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
import retrofit2.http.*

interface HomeDataAPI {
    // Real API responses
    @GET("v1/all_startups")
    suspend fun getStartupCardData(
    ): StartupDataResponse

    @GET("v1/all_investors")
    suspend fun getInvestorCardData(
    ): InvestorResponseData

    @GET("v1/all_investor_info")
    suspend fun getInvestorDetails(
    ): AllInvestorDetails

    @GET("v1/all_startup_info")
    suspend fun getStartupDetails(
    ): AllStartupDetails

    @POST("v1/startups/{startupID}/investors/{investorID}/like")
    suspend fun startupLikeInvestor(@Path("startupID")startupID: Int, @Path("investorID") investorID: Int ): SignupResponse

    @POST("v1/investors/{investorID}/startup/{startupID}/like")
    suspend fun investorLikeStartup(@Path("startupID")startupID: Int, @Path("investorID") investorID: Int ): SignupResponse


    @GET("v1/investors/{investorID}/likes")
    suspend fun getInvestorsLikedCards(@Path("investorID") investorID: Int ): MutableList<PendingMatchModel>

    @GET("v1/startups/{startupID}/likes")
    suspend fun getStartupsLikedCards(@Path("startupID") startupID: Int): MutableList<PendingMatchModel>

    // Mock responses
    @Mock
    @POST("v1/getmatched")
    @MockResponse(body = "[{\"imageURL\":\"https://cdn.dribbble.com/userupload/7889038/file/original-8a3114ac067714ed900bb8437175ec7c.jpg?compress=1&resize=1504x1128\",\"name\":\"ArrowHealth\",\"phoneNumber\":\"254702766735\"}," +
            "{\"imageURL\":\"https://cdn.dribbble.com/userupload/8098458/file/original-a2252dbb9fbd1b6fc989cea4156f9519.jpg?compress=1&resize=1338x1003&vertical=center\",\"name\":\"Drivable\",\"phoneNumber\":\"254111690030\"}]")
    suspend fun getMatchedCards(): MutableList<MatchedModel>


    @Mock
    @POST("v1/getInvestorMatched")
    @MockResponse(body = "[{\"imageURL\":\"https://images.pexels.com/photos/3769021/pexels-photo-3769021.jpeg?auto=compress&cs=tinysrgb&w=600\",\"name\":\"Madeline Mugo\",\"phoneNumber\":\"254702766735\"}," +
            "{\"imageURL\":\"https://images.pexels.com/photos/4173168/pexels-photo-4173168.jpeg?auto=compress&cs=tinysrgb&w=600\",\"name\":\"Amani Onyango\",\"phoneNumber\":\"254111690030\"}]")
    suspend fun getMatchedInvestorCards(): MutableList<MatchedModel>

}
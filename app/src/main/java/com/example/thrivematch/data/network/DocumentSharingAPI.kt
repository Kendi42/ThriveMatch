package com.example.thrivematch.data.network

import com.example.thrivematch.data.response.StartupDataResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DocumentSharingAPI {


    // Todo: Add response
    @GET("v1/startup/(startupID)/uploadDoc")
    suspend fun saveUploadedDocument(@Path("startupID") startupID: Int)

    @GET("v1/all_startups")
    suspend fun getDocumentList()

}
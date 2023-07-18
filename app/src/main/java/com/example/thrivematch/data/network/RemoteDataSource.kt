package com.example.thrivematch.data.network

import co.infinum.retromock.NonEmptyBodyFactory
import co.infinum.retromock.Retromock
import com.example.thrivematch.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RemoteDataSource {

    companion object{
        private const val BASE_URL= "https://spring-thrivematch.calmwater-2978c172.eastus.azurecontainerapps.io/api/"
    }

    fun <Api> buildApi(
        api: Class<Api>,
        authToken: String? = null
    ): Api{
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client( // Logging interceptor
                OkHttpClient.Builder().also { client ->
                        if(BuildConfig.DEBUG){
                            val logging = HttpLoggingInterceptor()
                            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                            client.addInterceptor(logging)
                        }
                    }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        // Create Retromock instance and wrap the Retrofit instance
        val retromock = Retromock.Builder()
            .retrofit(retrofit)
            .build()
        // Create the mocked AuthAPI
        return retromock.create(api)
    }
}
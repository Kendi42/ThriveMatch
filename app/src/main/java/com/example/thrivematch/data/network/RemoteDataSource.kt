package com.example.thrivematch.data.network

import com.example.thrivematch.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    companion object{
        private const val BASE_URL= "https://thrivematch.onrender.com/api/"
    }

    fun <Api> buildApi(
        api: Class<Api>,
        authToken: String? = null
    ): Api{
        return Retrofit.Builder()
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
            .create(api)

    }
}
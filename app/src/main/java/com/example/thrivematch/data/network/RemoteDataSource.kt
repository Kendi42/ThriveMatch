package com.example.thrivematch.data.network

import android.util.Log
import co.infinum.retromock.NonEmptyBodyFactory
import co.infinum.retromock.Retromock
import com.example.thrivematch.BuildConfig
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import com.example.thrivematch.util.Constants.AUTH_TOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RemoteDataSource() {
    companion object{
        private const val BASE_URL= "https://spring-thrivematch.calmwater-2978c172.eastus.azurecontainerapps.io/api/"
    }

    fun <Api> buildApi(
        api: Class<Api>,
    ): Api{
        Log.i("buildApi", "AuthToken inside buildApi: $AUTH_TOKEN")
        val httpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClientBuilder.addInterceptor(logging)
        }

        // Add Authorization header if the authToken is not null
        AUTH_TOKEN?.let {
            Log.i("Inside auth.let", "Inside auth.let")
            Log.i("Token is", "$it")
            val authInterceptor = Interceptor{ chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $it")
                    .build()
                chain.proceed(request)
            }
            httpClientBuilder.addInterceptor(authInterceptor)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClientBuilder.build())
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
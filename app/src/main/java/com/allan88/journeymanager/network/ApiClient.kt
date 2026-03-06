package com.allan88.journeymanager.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    /* IMPORTANT: Use your PC IP address */
    private const val BASE_URL = "http://192.168.1.5:8081/"

    /**
     * HTTP logging interceptor
     * Shows request/response in Logcat
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * OkHttp client with interceptors
     */
    private val client = OkHttpClient.Builder()
        .addInterceptor(TenantInterceptor())      // add tenant headers
        .addInterceptor(loggingInterceptor)       // log HTTP traffic
        .build()

    /**
     * Retrofit instance
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * API service
     */
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
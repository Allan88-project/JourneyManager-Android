package com.allan88.journeymanager.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        println("AUTH INTERCEPTOR EXECUTING")

        val originalRequest = chain.request()

        val token = TokenManager.getToken()

        println("TOKEN FROM MANAGER -> $token")

        val requestBuilder = originalRequest.newBuilder()

        if (!token.isNullOrBlank()) {

            requestBuilder.addHeader(
                "Authorization",
                "Bearer $token"
            )

            println("JWT SENT -> Bearer $token")
        }

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}
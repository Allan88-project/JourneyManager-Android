package com.allan88.journeymanager.network

import okhttp3.Interceptor
import okhttp3.Response

class TenantInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
            .newBuilder()
            .addHeader("X-Tenant-Id", "tenant1")
            .addHeader("X-User-Email", "user@tenant1.com")
            .build()

        return chain.proceed(request)
    }
}
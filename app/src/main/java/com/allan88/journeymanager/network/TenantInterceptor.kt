package com.allan88.journeymanager.network

import okhttp3.Interceptor
import okhttp3.Response

class TenantInterceptor(
    private val tenantId: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Tenant-Id", "tenant1")
            .build()

        return chain.proceed(request)
    }
}
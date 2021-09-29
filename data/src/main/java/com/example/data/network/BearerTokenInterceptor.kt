package com.example.data.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class BearerTokenInterceptor @Inject constructor(@Named("token") val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${token}\"")
            .build()

        return chain.proceed(request)
    }
}
package com.winteryy.readit.data.remote

import com.winteryy.readit.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

object NaverBookInterceptor: Interceptor {
    private const val HEADER_CLIENT_ID = "X-Naver-Client-Id"
    private const val HEADER_CLIENT_SECRET = "X-Naver-Client-Secret"

    private const val CLIENT_ID = BuildConfig.NAVER_CLIENT_ID
    private const val CLIENT_SECRET = BuildConfig.NAVER_CLIENT_SECRET

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .addHeader(HEADER_CLIENT_ID, CLIENT_ID)
            .addHeader(HEADER_CLIENT_SECRET, CLIENT_SECRET)
            .build()

        return chain.proceed(newRequest)
    }
}
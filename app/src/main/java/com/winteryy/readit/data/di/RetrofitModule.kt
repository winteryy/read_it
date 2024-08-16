package com.winteryy.readit.data.di

import com.google.gson.GsonBuilder
import com.winteryy.readit.data.remote.NaverBookInterceptor
import com.winteryy.readit.data.remote.search.NaverBookApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val NAVER_BOOK_BASE_URL = "https://openapi.naver.com/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideNaverBookInterceptor(): NaverBookInterceptor {
        return NaverBookInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClientWithNaverBookInterceptor(
        loggingInterceptor: HttpLoggingInterceptor,
        naverBookInterceptor: NaverBookInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(naverBookInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .setDateFormat("EEE, dd MMM YYYY HH:mm:ss XX")
                .create()
        )
    }

    @Provides
    @Singleton
    fun provideNaverBookApiService(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): NaverBookApiService {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .baseUrl(NAVER_BOOK_BASE_URL)
            .build()
            .create(NaverBookApiService::class.java)
    }
}
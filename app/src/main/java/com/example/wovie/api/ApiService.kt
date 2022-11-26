package com.example.wovie.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "0360553e8b280942cadda1ad5ef33f42"

interface ApiService {

    companion object {
        fun getInstance(): ApiService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder().apply {
                addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val originalRequest = chain.request()
                        val originalUrl = originalRequest.url
                        val newRequest = originalRequest.newBuilder().apply {
                            url(
                                originalUrl.newBuilder().addQueryParameter(
                                    "api_key", API_KEY
                                ).build()
                            )
                        }.build()
                        return chain.proceed(newRequest)
                    }
                })
            }
            httpClient.addInterceptor(logging)
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(ApiService::class.java)
        }
    }
}
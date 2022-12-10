package com.example.wovie.api

import com.example.wovie.api.response.FilmResponse
import com.example.wovie.api.response.NowPlaying
import com.example.wovie.api.response.Popular
import com.example.wovie.api.response.TopRated
import com.example.wovie.api.response.Upcoming
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://api.themoviedb.org/3/"
private const val API_KEY = "0360553e8b280942cadda1ad5ef33f42"
const val POPULAR_MOVIES_URL = "movie/popular"
const val NOW_PLAYING_URL = "movie/now_playing"
const val TOP_RATED_URL = "movie/top_rated"
const val UPCOMING_URL = "movie/upcoming"
const val MOVIE_BY_ID = "movie/{movie_id}"

interface ApiService {

    @GET(NOW_PLAYING_URL)
    suspend fun getNowPlayingMovies(): NowPlaying?
    @GET(POPULAR_MOVIES_URL)
    suspend fun getPopularMovies(): Popular?
    @GET(TOP_RATED_URL)
    suspend fun getTopRatedMovies(): TopRated?
    @GET(UPCOMING_URL)
    suspend fun getUpcomingMovies(): Upcoming?

    @GET(MOVIE_BY_ID)
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
    ): FilmResponse

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

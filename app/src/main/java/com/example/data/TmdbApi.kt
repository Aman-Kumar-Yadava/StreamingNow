package com.example.data

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TmdbApi {
    @GET("3/configuration")
    suspend fun getConfiguration(
        @Header("Authorization") authHeader: String?,
        @Query("api_key") apiKey: String?
    ): TmdbConfigurationResponse

    @GET("3/trending/movie/day")
    suspend fun getTrendingMovies(
        @Header("Authorization") authHeader: String?,
        @Query("api_key") apiKey: String?
    ): TmdbPageResponse<MediaItem>

    @GET("3/trending/tv/day")
    suspend fun getTrendingTvShows(
        @Header("Authorization") authHeader: String?,
        @Query("api_key") apiKey: String?
    ): TmdbPageResponse<MediaItem>
    
    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Header("Authorization") authHeader: String?,
        @Query("api_key") apiKey: String?
    ): TmdbPageResponse<MediaItem>
    
    @GET("3/tv/popular")
    suspend fun getPopularTvShows(
        @Header("Authorization") authHeader: String?,
        @Query("api_key") apiKey: String?
    ): TmdbPageResponse<MediaItem>
    
    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Header("Authorization") authHeader: String?,
        @Query("api_key") apiKey: String?
    ): TmdbPageResponse<MediaItem>
}

package com.example.pelisapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getAllMovies(
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean,
        @Query("include_video") includeVideo: Boolean,
        @Query("sort_by") sortBy: String
    ): Response<MovieResponseModel>;

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(@Path("movie_id") movieId: Int): Response<MovieDetailDataModel>

}
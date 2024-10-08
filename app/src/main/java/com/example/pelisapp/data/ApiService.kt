package com.example.pelisapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // Aquí van los métodos para interactuar con la API
    //para llamar la lista de peliculas usar @GET("popular")
    @GET("popular")
    suspend fun getPopularMovies(): Response<MovieResponseModel>

    //para llamar la detalle de la pelicula usar @GET("{movie_id}")
    @GET("{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Response<MovieDetailDataModel>



}
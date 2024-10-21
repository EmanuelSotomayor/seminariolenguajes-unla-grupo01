package com.example.pelisapp.data

import android.widget.Toast
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class MovieApiViewModel @Inject constructor(private val apiService: ApiService): ViewModel() {

    private val URL_SERVER_IMAGES: String = "https://image.tmdb.org/t/p/w300/";

    suspend fun getAllMovies(page: Int): MutableList<MovieDataModel>{

        val moviesResponse: Response<MovieResponseModel> = this.apiService.getAllMovies(page, false, false, "popularity.desc");
        var moviesContent: MutableList<MovieDataModel> = mutableListOf();

        if(moviesResponse.isSuccessful){
            moviesContent = moviesResponse.body()?.results?.stream()
                ?.map { movie ->
                    MovieDataModel(movie.id, movie.title, movie.releaseDate, "${URL_SERVER_IMAGES}${movie.poster}")
                }
                ?.collect(Collectors.toList())!!;
        }

        return moviesContent;

    }

    suspend fun getDetailMovieById(id: Int): MovieDetailDataModel{
        val movieDetailResponse: Response<MovieDetailDataModel> = this.apiService.getMovieDetailsById(id);
        var movieDetailContent = MovieDetailDataModel(0, "", "", "", "", 0);

        if(movieDetailResponse.isSuccessful){
            with(movieDetailResponse.body()!!){
                movieDetailContent = MovieDetailDataModel(id, title, "${URL_SERVER_IMAGES}${poster}", description, releaseDate, runtime)
            }
        }

        return movieDetailContent;
    }

}
package com.example.pelisapp.views.adapters

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.pelisapp.database.entities.FavoriteMovieEntity
import com.example.pelisapp.database.model.UserRepositoryViewModel
import com.example.pelisapp.data.MovieDetailDataModel
import kotlinx.coroutines.launch

class FavoriteMovieManager(
    private val userRepositoryViewModel: UserRepositoryViewModel,
    private val sharedPreferences: SharedPreferences,
    private val lifecycleScope: LifecycleCoroutineScope
) {
    fun addFavoriteMovie(movieDetail: MovieDetailDataModel) {
        lifecycleScope.launch {
            val userId = sharedPreferences.getInt("userId", -1)
            if (userId != -1) {
                val favoriteMovie = FavoriteMovieEntity(
                    name = movieDetail.title,
                    year = movieDetail.releaseDate.substring(0, 4), // Extraer el año
                    poster = movieDetail.poster,
                    timeDuraction = "${movieDetail.runtime} minutos"
                )
                Log.d("FavoriteMovieManager", "Adding favorite movie: ${favoriteMovie.movieId}")
                userRepositoryViewModel.addMovieToFavorites(userId, favoriteMovie)
            } else {
                Log.e("FavoriteMovieManager", "User ID not found in shared preferences!")
            }
        }
    }
}

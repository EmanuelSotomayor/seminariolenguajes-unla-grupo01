package com.example.pelisapp.database.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisapp.database.dao.FavoriteMovieDao
import com.example.pelisapp.database.entities.FavoriteMovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(private val favoriteMovieDao: FavoriteMovieDao) : ViewModel() {
    // Aquí puedes definir las funciones para interactuar con la base de datos
    fun getAllFavoriteMovies() {
        viewModelScope.launch {
            favoriteMovieDao.getAllFavoriteMovies()
        }
    }
    suspend fun getFavoriteMovieById(movieId: Int): FavoriteMovieEntity? {
        return favoriteMovieDao.getFavoriteMovieById(movieId)

    }
    init {
        // Esto debería acceder a la base de datos y provocar su creación
        viewModelScope.launch {
            val favoriteMovies = favoriteMovieDao.getAllFavoriteMovies()
        }

    }
}
package com.example.pelisapp.database.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.pelisapp.database.entitys.FavoriteMovieEntity
import com.example.pelisapp.database.entitys.UserWithMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRepositoryViewModel @Inject constructor(private val repository:UserRepository) : ViewModel() {
    // Función para agregar una película a las favoritas del usuario
    fun addMovieToFavorites(userId: Int, favoriteMovie: FavoriteMovieEntity) {
        viewModelScope.launch {
            repository.addFavoriteMovieForUser(userId, favoriteMovie)
        }
    }

    // Función para obtener las películas favoritas de un usuario
    fun getUserFavorites(userId: Int): LiveData<UserWithMovies> = liveData {
        val userWithMovies = repository.getUserWithMovies(userId)
        emit(userWithMovies)
    }
    // Función para eliminar una película favorita del usuario
    fun deleteFavoriteMovie(userId: Int, movieId: Int) {
        viewModelScope.launch {
            repository.deleteFavoriteMovie(userId, movieId)
        }
    }
}
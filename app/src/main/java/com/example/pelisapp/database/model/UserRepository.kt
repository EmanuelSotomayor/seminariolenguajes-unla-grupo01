package com.example.pelisapp.database.model

import com.example.pelisapp.database.dao.UserDao
import com.example.pelisapp.database.entities.FavoriteMovieEntity
import com.example.pelisapp.database.entities.UserMovieCrossRef
import com.example.pelisapp.database.entities.UserWithMovies
import javax.inject.Inject


class UserRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun addFavoriteMovieForUser(userId: Int, favoriteMovie: FavoriteMovieEntity) {
        val existingMovie = userDao.getMovieByName(favoriteMovie.name)
        /*val movieId = userDao.insertMovie(favoriteMovie)
        // Si la película ya existe (el ID es 0), no se agregará la relación.
        if (movieId != -1L) {
            val isFavorite = userDao.checkIfMovieIsFavorite(userId, movieId.toInt())
            if (isFavorite == 0) {

            val crossRef = UserMovieCrossRef(userId = userId, movieId = movieId.toInt())
            userDao.insertUserMovieCrossRef(crossRef)
            }else{
                println("La película ya está en favoritos")
            }
        }*/
        val movieId = if (existingMovie != null) {
            // Si la película ya existe, usa su movieId
            existingMovie.movieId
        } else {
            // Si no existe, insertamos la película y obtenemos su nuevo movieId
            userDao.insertMovie(favoriteMovie).toInt()
        }

        // Insertar la relación entre el usuario y la película (evitando duplicados)
        userDao.insertUserMovieCrossRef(UserMovieCrossRef(userId, movieId))
    }

    // Obtener las películas favoritas de un usuario
    suspend fun getUserWithMovies(userId: Int): UserWithMovies {
        return userDao.getUserWithMovies(userId)
    }
    // Función para eliminar la película favorita del usuario
    suspend fun deleteFavoriteMovie(userId: Int, movieId: Int) {
        userDao.deleteUserMovieCrossRef(userId, movieId)
    }
}
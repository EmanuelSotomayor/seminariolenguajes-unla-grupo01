package com.example.pelisapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pelisapp.database.entitys.FavoriteMovieEntity

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM favorite_movies")
    suspend fun getAllFavoriteMovies(): List<FavoriteMovieEntity>
    @Insert
    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovieEntity)
    @Insert
    suspend fun insertFavoriteFilm(film: FavoriteMovieEntity): Long

    @Delete
    suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies WHERE movieId = :movieId")
    suspend fun getFavoriteMovieById(movieId: Int): FavoriteMovieEntity?

    @Query("DELETE FROM favorite_movies WHERE movieId = :movieId")
    suspend fun deleteFavoriteFilm(movieId: Int)
}
package com.example.pelisapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pelisapp.database.entities.FavoriteMovieEntity
import com.example.pelisapp.database.entities.UserEntity
import com.example.pelisapp.database.entities.UserMovieCrossRef
import com.example.pelisapp.database.entities.UserWithMovies

@Dao
interface UserDao {
    // Operaciones CRUD para UserEntity

    //funcion para agregar un usuario
    @Insert
    suspend fun insertUser(user: UserEntity)

    //funcion para buscar un usuario por email
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    //funcion para verificar si el usuario existe
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email LIMIT 1)")
    suspend fun userExists(email: String): Boolean


    //funion para obtener lista de usuarios
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>


    // Otras operaciones CRUD para UserEntity relacionado con las películas favoritas

    //funcion para obtener un usuario por su ID
    @Query("SELECT * FROM users WHERE userId = :id")
    suspend fun getUserById(id: Int): UserEntity?
    @Insert
    suspend fun inserFavoriteMovie(favoriteMovie: FavoriteMovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserMovieCrossRef(crossRef: UserMovieCrossRef)
    // Obtener un usuario con sus películas favoritas
    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserWithMovies(userId: Int): UserWithMovies

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(favoriteMovie: FavoriteMovieEntity): Long

    @Query("SELECT COUNT(*) FROM user_movie_cross_ref WHERE userId = :userId AND movieId = :movieId")
    suspend fun checkIfMovieIsFavorite(userId: Int, movieId: Int): Int
    @Query("DELETE FROM user_movie_cross_ref WHERE userId = :userId AND movieId = :movieId")
    suspend fun deleteUserMovieCrossRef(userId: Int, movieId: Int)
    @Query("SELECT * FROM favorite_movies WHERE name = :movieName")
    suspend fun getMovieByName(movieName: String): FavoriteMovieEntity?






}
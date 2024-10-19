package com.example.pelisapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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

    @Insert
    suspend fun insertUserMovieCrossRef(crossRef: UserMovieCrossRef)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserWithMovies(userId: Int): List<UserWithMovies>


}
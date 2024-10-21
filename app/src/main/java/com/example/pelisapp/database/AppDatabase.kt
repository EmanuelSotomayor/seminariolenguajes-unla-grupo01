package com.example.pelisapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pelisapp.database.dao.FavoriteMovieDao
import com.example.pelisapp.database.dao.UserDao
import com.example.pelisapp.database.entitys.FavoriteMovieEntity
import com.example.pelisapp.database.entitys.UserEntity
import com.example.pelisapp.database.entitys.UserMovieCrossRef

@Database(entities = [UserEntity::class, FavoriteMovieEntity::class, UserMovieCrossRef::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase()  {
    abstract fun userDao(): UserDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
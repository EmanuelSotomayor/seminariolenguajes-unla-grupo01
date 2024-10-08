package com.example.pelisapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    /*Objeto que simula un método estatico para obtener una instancia de la conexión
    de la BD, usando el patrón de diseño Singleton*/
    companion object{

        private var INSTANCE: AppDatabase? = null;

        fun getDBInstance(context: Context): AppDatabase {

            if(INSTANCE == null){

                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "app_database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }

            }

            //Especificamos que el objeto INSTANCE nunca va a ser nulo.
            return INSTANCE!!;

        }

    }

}
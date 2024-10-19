package com.example.pelisapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "favorite_movies"
)
data class FavoriteMovieEntity(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "movieId") val movieId: Int,
    @ColumnInfo(name = "name")var name: String,
    @ColumnInfo(name = "year") var year: String,
    @ColumnInfo(name = "poster") var poster: String,
    @ColumnInfo(name = "timeDuraction") var timeDuraction:String
)

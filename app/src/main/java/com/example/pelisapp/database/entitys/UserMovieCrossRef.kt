package com.example.pelisapp.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(primaryKeys = ["userId", "movieId"],
    tableName = "user_movie_cross_ref")
data class UserMovieCrossRef(
    @ColumnInfo(name = "userId") val userId: Int,
    @ColumnInfo(name = "movieId") val movieId: Int)

package com.example.pelisapp.database.entitys

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithMovies(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "movieId",
        associateBy = Junction(UserMovieCrossRef::class)
        )
    val movies: List<FavoriteMovieEntity>
)


package com.example.pelisapp.models


import com.example.pelisapp.database.dao.FavoriteMovieDao
import com.example.pelisapp.database.entitys.FavoriteMovieEntity
import javax.inject.Inject

class FavoriteFilmProvider @Inject constructor(
    private val favoriteFilmDao: FavoriteMovieDao
) {
    suspend fun getFavoriteMovies(): List<FavoriteFilmModel> {
        val favoriteMovieEntities = favoriteFilmDao.getAllFavoriteMovies()
        return favoriteMovieEntities.toFavoriteFilmModelList()
    }
    private fun FavoriteMovieEntity.toFavoriteFilmModel(): FavoriteFilmModel {
        return FavoriteFilmModel(
            name = this.name,
            year = this.year,
            poster = this.poster,
            timeDuraction = this.timeDuraction,
            movieId = this.movieId
        )
    }
    private fun List<FavoriteMovieEntity>.toFavoriteFilmModelList(): List<FavoriteFilmModel> {
        return this.map { it.toFavoriteFilmModel() }
    }
    suspend fun addFavoriteMovie(favoriteFilmModel: FavoriteFilmModel) {
        val favoriteMovieEntity = favoriteFilmModel.toFavoriteMovieEntity()
        favoriteFilmDao.insertFavoriteMovie(favoriteMovieEntity)
    }
    private fun FavoriteFilmModel.toFavoriteMovieEntity(): FavoriteMovieEntity {
        return FavoriteMovieEntity(
            name = this.name,
            year = this.year,
            poster = this.poster,
            timeDuraction = this.timeDuraction
        )
    }
    suspend fun deleteFavoriteMovie(movieId: Int) {
        favoriteFilmDao.deleteFavoriteFilm(movieId)
    }
    companion object{
        val favoriteMoviesList = listOf<FavoriteFilmModel>(
            FavoriteFilmModel("El Señor de los Anillos: La Comunidad del Anillo", "2001", "https://upload.wikimedia.org/wikipedia/en/3/36/Madagascar_Theatrical_Poster.jpg", "178 minutos"),
            FavoriteFilmModel("El Padrino", "1972", "https://placehold.co/150x150.png", "175 minutos"),
            FavoriteFilmModel("Pulp Fiction", "1994", "https://placehold.co/150x150.png", "154 minutos"),
            FavoriteFilmModel("La Naranja Mecánica", "1971", "https://placehold.co/150x150.png", "156 minutos"),
            FavoriteFilmModel("Matrix", "1999", "https://placehold.co/150x150.png", "136 minutos")
        )
    }
}
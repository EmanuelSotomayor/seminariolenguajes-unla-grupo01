package com.example.pelisapp.models

class FavoriteFilmProvider {
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
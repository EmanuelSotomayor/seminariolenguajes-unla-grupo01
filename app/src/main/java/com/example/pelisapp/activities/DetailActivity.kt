package com.example.pelisapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.pelisapp.R
import com.example.pelisapp.data.MovieApiViewModel
import com.example.pelisapp.database.entitys.FavoriteMovieEntity
import com.example.pelisapp.database.model.UserRepositoryViewModel
import com.example.pelisapp.models.FilmModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
   private lateinit var button2: Button
   private lateinit var titleView: TextView
   private lateinit var descriptionView: TextView
   private lateinit var imageView: ImageView
    private lateinit var sharedPreferences2: SharedPreferences
    private val userRepositoryViewModel: UserRepositoryViewModel by viewModels()

    //el movieApiViewModel es lo que se usa para para acceder a las funciones de la api
    private  val movieApiViewModel: MovieApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        titleView = findViewById(R.id.titleView)
        descriptionView = findViewById(R.id.descriptionView)
        imageView = findViewById(R.id.imageView)
        sharedPreferences2 = getSharedPreferences("user_session", MODE_PRIVATE)
        val film = intent.getParcelableExtra<FilmModel>("film")
        if (film != null) {
            // Mostrar los detalles del film en la interfaz de usuario
            titleView.text = film.title
            descriptionView.text = film.info
            imageView.load(film.image)

        }else {
            Log.e("SecondActivity", "Film object is null!")
        }
        button2 = findViewById(R.id.button2)
        button2.setOnClickListener {
            if (film != null) {
                addFavoriteMovie(film)
            }else{
                Log.e("SecondActivity", "Film object is null!")
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addFavoriteMovie(film: FilmModel) {
        lifecycleScope.launch{
            val userId = sharedPreferences2.getInt("userId", -1)
            if (userId != -1) {
                val favoriteMovie = FavoriteMovieEntity(
                     // Room autogenera el ID
                    name = film.title,
                    year = "2023",
                    poster = film.image.toString(),
                    timeDuraction = "120 minutos"
                )
                Log.d("DetailActivity", "Adding favorite movie: ${favoriteMovie.movieId}")
                userRepositoryViewModel.addMovieToFavorites(userId, favoriteMovie)

            }else{
                Log.e("DetailActivity", "User ID not found in shared preferences!")
            }


        }
    }
}
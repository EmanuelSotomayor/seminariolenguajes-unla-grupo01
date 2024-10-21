package com.example.pelisapp.activities

import android.annotation.SuppressLint
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
import com.example.pelisapp.BottomFragment
import com.example.pelisapp.R
import com.example.pelisapp.TopFragment
import com.example.pelisapp.data.MovieApiViewModel
import com.example.pelisapp.data.MovieDetailDataModel
import com.example.pelisapp.database.entities.FavoriteMovieEntity
import com.example.pelisapp.database.model.UserRepositoryViewModel
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
    private val movieApiViewModel: MovieApiViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        sharedPreferences2 = getSharedPreferences("user_session", MODE_PRIVATE)
        button2 = findViewById(R.id.buttonSave)
        // Obtener el ID de la película que se pasó como extra
        val movieId = intent.getIntExtra("movie_id", -1)
        if (movieId != -1) {
            getMovieDetails(movieId)
        } else {
            Log.e("DetailActivity", "Movie ID is invalid!")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getMovieDetails(movieId: Int) {
        lifecycleScope.launch {
            try {
                val movieDetail = movieApiViewModel.getDetailMovieById(movieId)

                // Carga el TopFragment con título e imagen usando newInstance
                val topFragment = TopFragment.newInstance(movieDetail.title, movieDetail.poster)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_top_container, topFragment)
                    .commit()

                // Carga el BottomFragment con la descripción usando newInstance
                val bottomFragment = BottomFragment.newInstance(movieDetail.description)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_bottom_container, bottomFragment)
                    .commit()

                // Manejar el botón para agregar a favoritos
                button2.setOnClickListener {
                    addFavoriteMovie(movieDetail)
                }

            } catch (e: Exception) {
                Log.e("DetailActivity", "Error en la solicitud de detalles de la película", e)
            }
        }
    }

    private fun addFavoriteMovie(movieDetail: MovieDetailDataModel) {
        lifecycleScope.launch {
            val userId = sharedPreferences2.getInt("userId", -1)
            if (userId != -1) {
                val favoriteMovie = FavoriteMovieEntity(
                    name = movieDetail.title,
                    year = movieDetail.releaseDate.substring(0, 4),
                    poster = movieDetail.poster,
                    timeDuraction = "${movieDetail.runtime} minutos"
                )
                Log.d("DetailActivity", "Adding favorite movie: ${favoriteMovie.movieId}")
                userRepositoryViewModel.addMovieToFavorites(userId, favoriteMovie)
            } else {
                Log.e("DetailActivity", "User ID not found in shared preferences!")
            }
        }
    }
}
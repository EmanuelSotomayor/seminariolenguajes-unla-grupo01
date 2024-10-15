package com.example.pelisapp.activities

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisapp.R
import com.example.pelisapp.database.model.UserRepositoryViewModel
import com.example.pelisapp.database.model.UserViewModel
import com.example.pelisapp.databinding.ActivityFavoriteBinding
import com.example.pelisapp.models.FavoriteFilmModel
import com.example.pelisapp.models.FavoriteFilmProvider
import com.example.pelisapp.views.adapters.FavoriteFilmAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : BaseActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    private var favoriteMoviesMutableList: MutableList<FavoriteFilmModel> = FavoriteFilmProvider.favoriteMoviesList.toMutableList()
    private lateinit var adapter: FavoriteFilmAdapter
    private val userRepositoryViewModel: UserRepositoryViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityFavoriteBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        layoutInflater.inflate(R.layout.activity_favorite, findViewById(R.id.activity_content))
        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val recyclerView: RecyclerView=findViewById(R.id.recycleFavoritePeli)
        initRecyclerView(recyclerView)
        val userId = sharedPreferences.getInt("userId", -1)
        if (userId != -1) {
            // Cargar las películas favoritas del usuario
            loadFavoriteMovies(userId)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
   private fun initRecyclerView(recyclerView: RecyclerView){

        /*val recyclerView = findViewById<RecyclerView>(R.id.recycleFavoritePeli)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FavoritePelisAdapter(FavoritePelisProvider.favoriteMoviesList)*/
       adapter=FavoriteFilmAdapter(
           favoriteFilmModelList =  favoriteMoviesMutableList,
           onClickListener ={ favoriteFilmMovie -> onItemSelected(favoriteFilmMovie) },
           onClickDelete = {position -> onDeleteItem(position)}
       )

      /* binding.recycleFavoritePeli.layoutManager = LinearLayoutManager(this)
       binding.recycleFavoritePeli.adapter =adapter*/
       recyclerView.layoutManager = LinearLayoutManager(this)
       recyclerView.adapter = adapter
   }
    private fun onDeleteItem(position: Int) {
        /*favoriteMoviesMutableList.removeAt(position)
        adapter.notifyItemRemoved(position)*/
        val movieToDelete = favoriteMoviesMutableList[position]

        // Obtener el userId desde las SharedPreferences
        val userId = sharedPreferences.getInt("userId", -1)

        if (userId != -1) {
            // Eliminar la película favorita de la base de datos
            userRepositoryViewModel.deleteFavoriteMovie(userId, movieToDelete.movieId)

            // Eliminar la película de la lista local y actualizar el RecyclerView
            favoriteMoviesMutableList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
    }
    private fun onItemSelected(favoriteFilmModel: FavoriteFilmModel){

    }
    private fun loadFavoriteMovies(userId: Int) {
        userRepositoryViewModel.getUserFavorites(userId).observe(this) { userWithMovies ->
            if (userWithMovies != null) {
                favoriteMoviesMutableList.clear()
                favoriteMoviesMutableList.addAll(userWithMovies.movies.map { movie ->
                    FavoriteFilmModel(
                        name = movie.name,
                        year = movie.year,
                        poster = "https://placehold.co/150x150.png",
                        timeDuraction = movie.timeDuraction,
                        movieId = movie.movieId
                    )
                })
                adapter.notifyDataSetChanged()
            }
        }
    }
}
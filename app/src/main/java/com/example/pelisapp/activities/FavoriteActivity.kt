package com.example.pelisapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pelisapp.R
import com.example.pelisapp.databinding.ActivityFavoriteBinding
import com.example.pelisapp.models.FavoriteFilmModel
import com.example.pelisapp.models.FavoriteFilmProvider
import com.example.pelisapp.views.adapters.FavoriteFilmAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    @Inject
    lateinit var favoriteFilmProvider: FavoriteFilmProvider
    private lateinit var binding: ActivityFavoriteBinding
    private var favoriteMoviesMutableList: MutableList<FavoriteFilmModel> = mutableListOf()
    private lateinit var adapter: FavoriteFilmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       binding= ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        loadFavoriteMovies()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
   private fun initRecyclerView(){

        /*val recyclerView = findViewById<RecyclerView>(R.id.recycleFavoritePeli)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FavoritePelisAdapter(FavoritePelisProvider.favoriteMoviesList)*/
       adapter=FavoriteFilmAdapter(
           favoriteFilmModelList =  favoriteMoviesMutableList,
           onClickListener ={ favoriteFilmMovie -> onItemSelected(favoriteFilmMovie) },
           onClickDelete = {position -> onDeleteItem(position)}
       )

       binding.recycleFavoritePeli.layoutManager = LinearLayoutManager(this)
       binding.recycleFavoritePeli.adapter =adapter
   }
    private fun onDeleteItem(position: Int) {
        val movieToDelete = favoriteMoviesMutableList[position]
        //favoriteMoviesMutableList.removeAt(position)
        //adapter.notifyItemRemoved(position)
        CoroutineScope(Dispatchers.IO).launch {
            favoriteFilmProvider.deleteFavoriteMovie(movieToDelete.movieId)

            // Elimina el ítem de la lista mutable
            withContext(Dispatchers.Main) {
                favoriteMoviesMutableList.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }
    }
    private fun onItemSelected(favoriteFilmModel: FavoriteFilmModel){

    }
    @SuppressLint("NotifyDataSetChanged")
    private fun loadFavoriteMovies(){
        CoroutineScope(Dispatchers.Main).launch {
            val movies = favoriteFilmProvider.getFavoriteMovies()
            withContext(Dispatchers.Main) {
                // Limpiar la lista actual y agregar las nuevas películas
                favoriteMoviesMutableList.clear()
                favoriteMoviesMutableList.addAll(movies)
                // Notificar al adaptador que los datos han cambiado
                //adapter.notifyDataSetChanged()
                val newIndex = favoriteMoviesMutableList.size - 1
                adapter.notifyItemInserted(newIndex)
            }
        }
    }

    private fun addFavoriteMovie(movie: FavoriteFilmModel) {
        CoroutineScope(Dispatchers.IO).launch {
            // Insertar la nueva película en la base de datos
            favoriteFilmProvider.addFavoriteMovie(movie)

            // Cargar la lista de películas actualizada
            loadFavoriteMovies()  // Esto refresca la UI
        }
    }
}
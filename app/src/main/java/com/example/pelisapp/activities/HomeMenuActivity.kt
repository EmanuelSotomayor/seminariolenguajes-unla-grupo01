package com.example.pelisapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisapp.models.FilmModel
import com.example.pelisapp.views.adapters.FilmRecyclerViewAdapter
import com.example.pelisapp.MainActivity
import com.example.pelisapp.R
import com.example.pelisapp.data.MovieApiViewModel
import com.example.pelisapp.data.MovieDataModel
import com.example.pelisapp.data.MovieDetailDataModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.stream.Collectors

@AndroidEntryPoint
class HomeMenuActivity : BaseActivity() {

    lateinit var films: MutableList<FilmModel>
    lateinit var adapter: FilmRecyclerViewAdapter
    lateinit var imageView: ImageView
    private var currentPage = 1
    private var isLoading = false //Para evitar cargas inecesarias agregamos un loader
    private lateinit var sharedPreferences: android.content.SharedPreferences
    private val movieApiViewModel: MovieApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        layoutInflater.inflate(R.layout.home_menu, findViewById(R.id.activity_content))

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_menu)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        films = mutableListOf();

        val recyclerView: RecyclerView = findViewById(R.id.filmsRecyclerView)
        adapter = FilmRecyclerViewAdapter(this, films)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        //Agregamos un listener para el scrolling
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);

                val layoutManager = recyclerView.layoutManager as GridLayoutManager;
                val totalItemCount = layoutManager.itemCount;
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                // Cargamos más películas si estamos cerca del final y no estamos cargando.
                if (!isLoading && totalItemCount <= (lastVisibleItem + 2)) {
                    loadMoreFilms();
                }
            }
        })

        adapter.setOnClickListener { view ->
            val position = recyclerView.getChildAdapterPosition(view!!)
            val intent = Intent(this@HomeMenuActivity, DetailActivity::class.java)
            intent.putExtra("film", films[position])
            startActivity(intent)
        }

        imageView = findViewById(R.id.imageView)
        imageView.setOnClickListener {
            logout()
        }

        fillFilms();

    }

    private fun fillFilms() {
        /*Usamos una corutina para traer todas las peliculas y luego el bloque async-awaitAll
        para llamar al otro endpoint que trae los detalles de las peliculas y esperamos a
        que terminen todas las llamadas*/
        CoroutineScope(Dispatchers.IO).launch {
            //La primera carga va a iniciar desde la página 1, porque en la API es la página principal, no 0.
            val movieList = movieApiViewModel.getAllMovies(currentPage);

            val filmModels = movieList.map { movie ->
                async {
                    val movieDetail: MovieDetailDataModel = movieApiViewModel.getDetailMovieById(movie.id);
                    val descriptionFilm = movieDetail.description;
                    FilmModel(movie.poster, movie.title, descriptionFilm);
                }
            }.awaitAll();

            withContext(Dispatchers.Main) {
                films.clear();
                films.addAll(filmModels);
                //Notificamos al adapter cuando hay cambios
                adapter.notifyDataSetChanged();
                println("Loaded films: $films");
            }
        }

    }

    private fun loadMoreFilms() {
        //Cambiamos el estado del laoder a true, hasta que termine de realizarse la petición
        isLoading = true;
        CoroutineScope(Dispatchers.IO).launch {
            /*Obtenemos peliculas de la siguiente página, cada que llegamos al final,
            es decir, se va a ir aumentando la variable currentPage de 1 en 1.*/
            val newMovies = movieApiViewModel.getAllMovies(currentPage++);

            val filmModels = newMovies.map { movie ->
                async {
                    val movieDetail: MovieDetailDataModel = movieApiViewModel.getDetailMovieById(movie.id);
                    FilmModel(movie.poster, movie.title, movieDetail.description);
                }
            }.awaitAll();

            withContext(Dispatchers.Main) {
                //Agregamos los nuevos elementos al adapter
                adapter.addFilms(filmModels);
                //Cambiamos nuevamente el estado del loader a false, porque ya terminó de cargar.
                isLoading = false;
            }
        }
    }

    private fun logout() {
        // Eliminar todos los datos almacenados en SharedPreferences
        clearAllStoredData()

        // Navegar a la pantalla de inicio de sesión
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Finalizar la actividad actualh
    }
    private fun clearAllStoredData() {
        // Accede a SharedPreferences
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)

        // Crear el editor para modificar SharedPreferences
        val editor = sharedPreferences.edit()

        // Limpiar todas las referencias guardadas
        editor.clear()

        // Aplicar los cambios
        editor.apply()

        // Informar al usuario (opcional)
        Toast.makeText(this, "Todas las referencias eliminadas", Toast.LENGTH_SHORT).show()
    }

}

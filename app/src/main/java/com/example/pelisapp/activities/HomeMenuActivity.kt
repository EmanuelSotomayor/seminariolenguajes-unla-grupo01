package com.example.pelisapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisapp.models.FilmModel
import com.example.pelisapp.views.adapters.FilmRecyclerViewAdapter
import com.example.pelisapp.MainActivity
import com.example.pelisapp.R

class HomeMenuActivity : AppCompatActivity() {

    lateinit var films: ArrayList<FilmModel>
    lateinit var imageView: ImageView
    private lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_menu)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_menu)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.filmsRecyclerView)

        fillFilms()

        val adapter = FilmRecyclerViewAdapter(this, films)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter.setOnClickListener { view ->
            val position = recyclerView.getChildAdapterPosition(view!!)
            val intent = Intent(this@HomeMenuActivity, DetailActivity::class.java)
            Log.d("HomeMenuActivity", "Film clicked: $position")
            Log.d("HomeMenuActivity", "Film title: ${films[position].title}")
            Log.d("HomeMenuActivity", "Film info: ${films[position].info}")
            //intent.putExtra("film", films[position])
            startActivity(intent)
        }
        imageView = findViewById(R.id.imageView)
        imageView.setOnClickListener {
            logout()
        }
    }

    private fun fillFilms() {
        val titles = resources.getStringArray(R.array.example_films)
        val infos = resources.getStringArray(R.array.example_films_info)

        films = ArrayList()

        for (i in titles.indices) {
           val filmImage = when (i) {
               0 -> R.drawable.interestellar
               1 -> R.drawable.fury
               2 -> R.drawable.truman
               3 -> R.drawable.the_game
               else -> R.drawable.baseline_video_camera_back_24
           }
            val film = FilmModel(filmImage, titles[i], infos[i])
            films.add(film)
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

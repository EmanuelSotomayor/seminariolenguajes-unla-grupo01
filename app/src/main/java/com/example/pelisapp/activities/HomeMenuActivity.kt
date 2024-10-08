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

class HomeMenuActivity : BaseActivity() {

    lateinit var films: ArrayList<FilmModel>
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        layoutInflater.inflate(R.layout.home_menu, findViewById(R.id.activity_content))
        //setContentView(R.layout.home_menu)

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
           /* Log.d("HomeMenuActivity", "Film clicked: $position")
            Log.d("HomeMenuActivity", "Film title: ${films[position].title}")
            Log.d("HomeMenuActivity", "Film info: ${films[position].info}")*/
            intent.putExtra("film", films[position])
            startActivity(intent)
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


}

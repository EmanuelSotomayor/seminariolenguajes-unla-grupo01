package com.example.pelisapp.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pelisapp.BottomFragment
import com.example.pelisapp.R
import com.example.pelisapp.TopFragment
import com.example.pelisapp.models.FilmModel

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtiene el objeto FilmModel desde el Intent
       val film = intent.getParcelableExtra<FilmModel>("film")
       if (film != null){
           // Carga el TopFragment con título e imagen usando newInstance
           val topFragment = TopFragment.newInstance(film.title, film.image)

           supportFragmentManager.beginTransaction()
               .replace(R.id.fragment_top_container,topFragment)
               .addToBackStack(null)
               .commit()

           // Carga el BottomFragment con la descripción usando newInstance
           val bottomFragment = BottomFragment.newInstance(film.info)
           supportFragmentManager.beginTransaction()
               .replace(R.id.fragment_bottom_container,bottomFragment)
               .addToBackStack(null)
               .commit()
       }else{
           Log.e("DetailActivity","Film object is null!")
       }

    }
}
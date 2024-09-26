package com.example.pelisapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.example.pelisapp.R
import com.example.pelisapp.models.FilmModel

class DetailActivity : AppCompatActivity() {
   private lateinit var button2: Button
   private lateinit var titleView: TextView
   private lateinit var descriptionView: TextView
   private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        titleView = findViewById(R.id.titleView)
        descriptionView = findViewById(R.id.descriptionView)
        imageView = findViewById(R.id.imageView)
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
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
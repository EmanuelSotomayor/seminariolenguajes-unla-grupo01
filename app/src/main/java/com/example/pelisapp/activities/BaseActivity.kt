package com.example.pelisapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pelisapp.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
    }
    // Inflar el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Manejar las acciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
                // Acción para Home
                if (this !is HomeMenuActivity){
                val intent = Intent(this, HomeMenuActivity::class.java)
                startActivity(intent)
                }
                true
            }
            R.id.action_favorite -> {
                // Acción para Favoritos
                if (this !is FavoriteActivity){
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                }
                true
            }
            R.id.action_user -> {
                // Acción para Usuario
                if (this !is UserActivity){
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
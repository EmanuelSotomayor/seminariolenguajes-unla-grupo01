package com.example.pelisapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pelisapp.R

class UserActivity : BaseActivity() {
    private lateinit var sharedPreferences: android.content.SharedPreferences
   private lateinit var textUserName: TextView
    private lateinit var btnLogout: Button
    private lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        layoutInflater.inflate(R.layout.activity_user,findViewById(R.id.activity_content))
        //setContentView(R.layout.activity_user)
        textUserName = findViewById(R.id.textUserName)
        btnLogout = findViewById(R.id.btnLogout)
        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        userName = sharedPreferences.getString("username", "") ?: ""
        textUserName.text = userName
        btnLogout.setOnClickListener {
            logout()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun logout() {
        // Eliminar todos los datos almacenados en SharedPreferences
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val isCheckboxChecked = sharedPreferences.getBoolean("isChecked", false)
        if (!isCheckboxChecked) {
            clearAllStoredData()
        }

        // Navegar a la pantalla de inicio de sesión
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Finalizar la actividad actual
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

    }
}
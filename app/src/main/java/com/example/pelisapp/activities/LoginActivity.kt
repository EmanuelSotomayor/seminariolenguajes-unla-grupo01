package com.example.pelisapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pelisapp.MainActivity
import com.example.pelisapp.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun navigateToMainActivity(): Unit{
        val intentNavigateToMain = Intent(this, MainActivity::class.java);
        startActivity(intentNavigateToMain);
    }

    private fun navigateToRegisterActivity(): Unit{
        //val intentNavigateToRegister = Intent(this, RegisterActivity::class.java);
        //startActivity(intentNavigateToRegister);
    }

}
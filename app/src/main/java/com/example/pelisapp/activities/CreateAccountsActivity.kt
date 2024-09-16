package com.example.pelisapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pelisapp.R
import com.example.pelisapp.database.model.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CreateAccountsActivity : AppCompatActivity() {
    lateinit var emailInput : EditText
    lateinit var contraseñaInput : EditText
    lateinit var confContraseñaInput : EditText
    lateinit var registrarBtn : Button
    lateinit var ingresarBtn : TextView
    private val userViewModel: UserViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_accounts)

        emailInput = findViewById(R.id.editEmail)
        contraseñaInput = findViewById(R.id.editContraseña)
        confContraseñaInput = findViewById(R.id.editConfContraseña)
        registrarBtn = findViewById(R.id.btnRegistro)
        ingresarBtn = findViewById(R.id.btnIngresar)



        registrarBtn.setOnClickListener {
            val email = emailInput.text.toString()
            val contraseña = contraseñaInput.text.toString()
            val confContraseña = confContraseñaInput.text.toString()
            //val user = UserEntity(name = "juan", email = email, password = contraseña)
            lifecycleScope.launch {

             //userViewModel.addUser(user)
            }
            val intent = Intent(this, HomeMenuActivity::class.java)
            startActivity(intent)
        }
        ingresarBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}

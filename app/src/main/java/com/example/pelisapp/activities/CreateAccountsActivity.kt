package com.example.pelisapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge

import androidx.appcompat.app.AppCompatActivity
import com.example.pelisapp.R

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateAccountsActivity : AppCompatActivity() {
    lateinit var emailInput : EditText
    lateinit var contraseñaInput : EditText
    lateinit var confContraseñaInput : EditText
    lateinit var registrarBtn : Button
    lateinit var ingresarBtn : TextView


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
            //Log.i("Cuentas creadas --> ", "Email : $email , contraseña : $contraseña y confirmacion : $confContraseña")
            //val user = UserEntity(name = "juan", email = email, password = contraseña)

            val intent = Intent(this, HomeMenuActivity::class.java)
            startActivity(intent)
        }
        ingresarBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

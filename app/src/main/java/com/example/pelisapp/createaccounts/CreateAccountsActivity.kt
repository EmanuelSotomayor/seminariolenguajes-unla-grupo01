package com.example.pelisapp.createaccounts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pelisapp.HomeMenuActivity
import com.example.pelisapp.R
import com.example.pelisapp.activities.LoginActivity

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

            val intent = Intent(this, HomeMenuActivity::class.java)
            startActivity(intent)
        }
        ingresarBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

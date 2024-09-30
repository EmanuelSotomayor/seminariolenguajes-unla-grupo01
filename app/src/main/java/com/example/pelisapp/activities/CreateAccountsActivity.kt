package com.example.pelisapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
    lateinit var nameInput : EditText

    private val userViewModel: UserViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(isUserRegistered()){
            val intent = Intent(this@CreateAccountsActivity,HomeMenuActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_accounts)

        emailInput = findViewById(R.id.editEmail)
        contraseñaInput = findViewById(R.id.editContraseña)
        confContraseñaInput = findViewById(R.id.editConfContraseña)
        registrarBtn = findViewById(R.id.btnRegistro)
        ingresarBtn = findViewById(R.id.btnIngresar)
        nameInput = findViewById(R.id.editNombre)



        registrarBtn.setOnClickListener {

            val email = emailInput.text.toString()
            val contraseña = contraseñaInput.text.toString()
            val confContraseña = confContraseñaInput.text.toString()
            //val nombre = nombreInput.text.toString()

            //val user = UserEntity(email = email, contraseña = contraseña,confContraseña = confContraseña);
          if (validarCampos(email,contraseña,confContraseña)){
            lifecycleScope.launch {
                if(userExits(email)){
                    emailInput.error = "Este email ya esta registrado"
                }else {
                    val user = UserEntity(email = email,contraseña = contraseña,confContraseña = confContraseña)
                    userViewModel.addUser(user)
                    val userId = userViewModel.getUserByEmail(email)
                    val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("email",email)
                    editor.putString("password",contraseña)
                    editor.putInt("userId",userId?.userId ?: -1)
                    editor.putBoolean("isRegistered",true)
                    editor.apply()

                    val intent = Intent(this@CreateAccountsActivity,HomeMenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
          }
        }

        ingresarBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validarCampos(email: String,contraseña: String,confContraseña: String):Boolean{
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (email.isEmpty()) {
            emailInput.error = "El email no puede estar vacío"
            return false
        } else if (!email.matches(emailPattern.toRegex())) {
            emailInput.error = "Ingrese un correo electrónico válido"
            return false
        }

        if(contraseña.isEmpty()){
            contraseñaInput.error = "La contraseña no puede estar vacia"
            return false
        }else if(contraseña.length < 6){
            contraseñaInput.error = "La contraseña debe tener al menos 6 caracteres"
            return false
        }

        if(confContraseña.isEmpty()){
            confContraseñaInput.error = "Debe confirmar contraseña"
            return false
        }else if (contraseña != confContraseña){
            confContraseñaInput.error = "Las contraseñas no son iguales"
            return false
        }
        //Si pasa todas las validaciones,devuelve true
        return true
    }

    private suspend fun userExits(email: String): Boolean{
        val user = userViewModel.userExists(email)
        return user
    }

    private fun isUserRegistered(): Boolean {
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isRegistred",false)
    }

}

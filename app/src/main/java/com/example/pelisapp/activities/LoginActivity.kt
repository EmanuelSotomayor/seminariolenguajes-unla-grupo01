package com.example.pelisapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pelisapp.R
import com.example.pelisapp.database.model.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var loginBtn: Button;
    private lateinit var signUpBtn: Button;
    private lateinit var inputEmail: EditText;
    private lateinit var inputPassword: EditText;
    private lateinit var checkboxRememberUser: CheckBox;
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        this.loginBtn = findViewById(R.id.btnLogin);
        this.signUpBtn = findViewById(R.id.btnSignUp);
        this.inputEmail = findViewById(R.id.inputEmail);
        this.inputPassword = findViewById(R.id.inputPassword);
        this.checkboxRememberUser = findViewById(R.id.checkBoxRememberUser);

        loadRememberedUser()

        this.loginBtn.setOnClickListener {
            val emailValue = this.inputEmail.text.toString();
            val passwordValue = this.inputPassword.text.toString();
            if(emailValue.isEmpty() || passwordValue.isEmpty()) {
                Toast.makeText(this, "The inputs can't be empty", Toast.LENGTH_SHORT).show()
            }else{
                if(this.checkboxRememberUser.isChecked){
                    rememberUser(emailValue)
                    Toast.makeText(this, "${emailValue} remembered", Toast.LENGTH_SHORT).show()
                } else {
                    forgetUser()
                }
                this.navigateToMainActivity();
            }
        }

        this.signUpBtn.setOnClickListener{
            this.navigateToRegisterActivity();
        }

    }

    private fun navigateToMainActivity(): Unit{
        val intentNavigateToMain = Intent(this, HomeMenuActivity::class.java);
        startActivity(intentNavigateToMain);
    }

    private fun navigateToRegisterActivity(): Unit{
        val intentNavigateToRegister = Intent(this, CreateAccountsActivity::class.java);
        startActivity(intentNavigateToRegister);
    }

    private fun navigateToForgotPasswordActivity(): Unit{
        //val intentNavigateToForgotPassword = Intent(this, ForgotPasswordActivity::class.java);
        //startActivity(intentNavigateToForgotPassword);
    }

    private fun rememberUser(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("REMEMBERED_EMAIL", email)
        editor.putBoolean("REMEMBERED", true)
        editor.apply()
    }

    private fun loadRememberedUser(): Unit{
        val remembered = sharedPreferences.getBoolean("REMEMBERED", false)
        if (remembered) {
            val rememberedEmail = sharedPreferences.getString("REMEMBERED_EMAIL", "")
            inputEmail.setText(rememberedEmail)
            checkboxRememberUser.isChecked = true
        }
    }

    private fun forgetUser() {
        val editor = sharedPreferences.edit()
        editor.remove("REMEMBERED_EMAIL")
        editor.putBoolean("REMEMBERED", false)
        editor.apply()
    }

}
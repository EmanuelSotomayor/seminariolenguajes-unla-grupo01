package com.example.pelisapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pelisapp.HomeMenuActivity
import com.example.pelisapp.MainActivity
import com.example.pelisapp.R
import com.example.pelisapp.createaccounts.CreateAccountsActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBtn: Button;
    private lateinit var signUpBtn: Button;
    private lateinit var inputEmail: EditText;
    private lateinit var inputPassword: EditText;
    private lateinit var checkboxRememberUser: CheckBox;

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.loginBtn = findViewById(R.id.btnLogin);
        this.signUpBtn = findViewById(R.id.btnSignUp);
        this.inputEmail = findViewById(R.id.inputEmail);
        this.inputPassword = findViewById(R.id.inputPassword);
        this.checkboxRememberUser = findViewById(R.id.checkBoxRememberUser);

        this.loginBtn.setOnClickListener {
            val emailValue = this.inputEmail.text.toString();
            val passwordValue = this.inputPassword.text.toString();
                if(emailValue.isEmpty() || passwordValue.isEmpty()) {
                    Toast.makeText(this, "The inputs can't be empty", Toast.LENGTH_SHORT).show()
                }else{
                    if(this.checkboxRememberUser.isChecked){
                        Toast.makeText(this, "${emailValue} Remembered email", Toast.LENGTH_SHORT).show()
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

}
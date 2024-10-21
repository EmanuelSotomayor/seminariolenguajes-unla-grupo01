package com.example.pelisapp.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.pelisapp.R
import com.example.pelisapp.database.dao.UserDao
import com.example.pelisapp.database.entities.UserEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    private lateinit var loginBtn: Button;
    private lateinit var signUpBtn: Button;
    private lateinit var inputEmail: EditText;
    private lateinit var inputPassword: EditText;
    private lateinit var checkboxRememberUser: CheckBox;
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferences2: SharedPreferences
    @Inject
    lateinit var userDao: UserDao;
    //Expresión regular para válidar el formato del email ingresado
    private val EMAIL_PATTERN: String = "[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        createNotificationChannel()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*Usuario de prueba para probar la validación de login
        lifecycleScope.launch {
            val DB_INSTANCE: AppDatabase = AppDatabase.getDBInstance(this@LoginActivity);
            val userEntity = async { DB_INSTANCE.userDao().insertUser(UserEntity(0, "Test", "usuariotest@unla.com", "passwordTest")) }
        }*/

        sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        sharedPreferences2 = getSharedPreferences("user_session", MODE_PRIVATE)

        this.loginBtn = findViewById(R.id.btnLogin);
        this.signUpBtn = findViewById(R.id.btnSignUp);
        this.inputEmail = findViewById(R.id.inputEmail);
        this.inputPassword = findViewById(R.id.inputPassword);
        this.checkboxRememberUser = findViewById(R.id.checkBoxRememberUser);

        loadRememberedUser()

        this.loginBtn.setOnClickListener {

            val emailValue = this.inputEmail.text.toString();
            val passwordValue = this.inputPassword.text.toString();

            if(this.checkboxRememberUser.isChecked){
                rememberUser(emailValue)
                Toast.makeText(this, "${emailValue} remembered", Toast.LENGTH_SHORT).show()
            } else {
                forgetUser()
            }

            if(emailValue.isEmpty() || passwordValue.isEmpty()) {
                Toast.makeText(this, "The inputs can't be empty", Toast.LENGTH_SHORT).show()
            }else{
                if(!this.emailIsValid(emailValue)){
                    Toast.makeText(this, "Email format aren't valid", Toast.LENGTH_SHORT).show()
                }else{
                    lifecycleScope.launch(Dispatchers.IO){
                        val userEntity = async { userDao.getUserByEmail(emailValue) }
                            .await();
                        if(credentialsAreValid(userEntity, passwordValue)){
                            if (userEntity != null) {
                                sharedPreferences2.edit().putInt("userId", userEntity.userId).apply()
                                sharedPreferences2.edit().putString("name", userEntity.name).apply()
                                sharedPreferences2.edit().putBoolean("isLogged", true).apply()
                                sharedPreferences2.edit().putBoolean("isRegistered", true).apply()

                            }
                            navigateToMainActivity();
                        }else{
                            /*Como no se puede mostrar un toast en un hilo que no sea de UI,
                            se muestra usando el hilo principal*/
                            runOnUiThread {
                                Toast.makeText(this@LoginActivity, "Email or password is invalid", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

        }

        this.signUpBtn.setOnClickListener{
            this.navigateToRegisterActivity();
        }

    }

    private fun createNotificationChannel() {
        val channelId = "user_remember_channel"
        val channelName = "User Remember Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        channel.description = "Channel for User Remember notifications"

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)
    }

    private fun showUserRememberedNotification(email: String) {
        val channelId = "user_remember_channel"

        val intent = Intent(this, HomeMenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_person_24)
            .setContentTitle("Usuario recordado")
            .setContentText("La proxima vez que inicies sesion, tu usuario:  $email sera recordado.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }

    private fun navigateToMainActivity(): Unit{
        val intentNavigateToMain = Intent(this, HomeMenuActivity::class.java);
        startActivity(intentNavigateToMain);
    }

    private fun navigateToRegisterActivity(): Unit{
        val intentNavigateToRegister = Intent(this, CreateAccountsActivity::class.java);
        startActivity(intentNavigateToRegister);
    }

    /*private fun navigateToForgotPasswordActivity(): Unit{
        val intentNavigateToForgotPassword = Intent(this, ForgotPasswordActivity::class.java);
        startActivity(intentNavigateToForgotPassword);
    }*/

    private fun rememberUser(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("REMEMBERED_EMAIL", email)
        editor.putBoolean("REMEMBERED", true)
        editor.apply()
        showUserRememberedNotification(email)
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

    private fun emailIsValid(email: String): Boolean{
        return Pattern.matches(this.EMAIL_PATTERN, email);
    }

    private fun credentialsAreValid(userEntity: UserEntity?, password: String): Boolean{
        return userEntity != null && userEntity.password == password;
    }

}
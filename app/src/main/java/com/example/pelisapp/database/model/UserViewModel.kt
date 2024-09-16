package com.example.pelisapp.database.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisapp.database.dao.UserDao
import com.example.pelisapp.database.entitys.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {


    suspend fun addUser(user: UserEntity) = userDao.insertUser(user)
    suspend fun getAllUsers(): List<UserEntity> = userDao.getAllUsers()
    suspend fun getUserById(id: Int): UserEntity? = userDao.getUserById(id)
    suspend fun userExists(email: String): Boolean {
        val user = userDao.getUserByEmail(email)
        return user != null
    }

    init {
        // acceder a la base de datos y provocar su creación
        viewModelScope.launch {
            val users = userDao.getAllUsers()
        }
    }
}
package com.example.pelisapp.data

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieApiViewModel @Inject constructor(private val apiService: ApiService): ViewModel() {
    // Aquí puedes definir las funciones para interactuar con la API

}
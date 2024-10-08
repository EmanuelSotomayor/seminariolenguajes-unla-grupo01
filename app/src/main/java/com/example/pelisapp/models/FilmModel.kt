package com.example.pelisapp.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class FilmModel(
        val image: Int,
        val title: String,
        val info: String
): Parcelable

package com.example.pelisapp.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class FilmModel(
        val image: String,
        val title: String,
        val info: String,
        val id: Int=0
): Parcelable

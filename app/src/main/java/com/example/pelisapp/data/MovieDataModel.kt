package com.example.pelisapp.data

import com.google.gson.annotations.SerializedName

data class MovieDataModel(
    @SerializedName("id") val id:Int,
    @SerializedName("original_title") val title:String,
    @SerializedName("release_date") val releaseDate:String,
    @SerializedName("poster_path") val poster:String
)

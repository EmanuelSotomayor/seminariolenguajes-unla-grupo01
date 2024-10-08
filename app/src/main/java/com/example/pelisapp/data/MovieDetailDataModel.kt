package com.example.pelisapp.data

import com.google.gson.annotations.SerializedName

data class MovieDetailDataModel(
    @SerializedName("id") val id:Int,
    @SerializedName("original_title") val title:String,
    @SerializedName("poster_path") val poster :String,
    @SerializedName("overview") val description:String,
    @SerializedName("release_date") val releaseDate:String,
    @SerializedName("runtime") val runtime:Int
)

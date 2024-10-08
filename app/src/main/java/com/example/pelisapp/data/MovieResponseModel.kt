package com.example.pelisapp.data

import com.google.gson.annotations.SerializedName

data class MovieResponseModel(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieDataModel>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

package com.example.pelisapp.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisapp.R
import com.example.pelisapp.models.FavoriteFilmModel

class FavoriteFilmAdapter(private val favoriteFilmModelList: List<FavoriteFilmModel>): RecyclerView.Adapter<FavoriteFilmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFilmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoriteFilmViewHolder(layoutInflater.inflate(R.layout.item_favoritepelis, parent, false))
    }

    override fun getItemCount(): Int= favoriteFilmModelList.size

    override fun onBindViewHolder(holder: FavoriteFilmViewHolder, position: Int) {
        val item = favoriteFilmModelList[position]
        holder.render(item)

    }

}
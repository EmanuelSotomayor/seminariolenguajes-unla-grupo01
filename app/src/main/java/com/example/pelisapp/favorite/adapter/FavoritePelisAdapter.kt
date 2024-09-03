package com.example.pelisapp.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisapp.R
import com.example.pelisapp.favorite.favoritePelis

class FavoritePelisAdapter(private val favoritePelisList: List<favoritePelis>): RecyclerView.Adapter<FavoritePelisViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePelisViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritePelisViewHolder(layoutInflater.inflate(R.layout.item_favoritepelis, parent, false))
    }

    override fun getItemCount(): Int= favoritePelisList.size

    override fun onBindViewHolder(holder: FavoritePelisViewHolder, position: Int) {
        val item = favoritePelisList[position]
        holder.render(item)

    }

}
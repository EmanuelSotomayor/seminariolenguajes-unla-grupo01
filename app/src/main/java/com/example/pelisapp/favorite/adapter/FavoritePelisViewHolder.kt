package com.example.pelisapp.favorite.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pelisapp.R
import com.example.pelisapp.favorite.favoritePelis
import coil.load
import com.example.pelisapp.databinding.ItemFavoritepelisBinding

class FavoritePelisViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val bindin= ItemFavoritepelisBinding.bind(view)

 /*  val favoriteName = view.findViewById<TextView>(R.id.tvFavoriteName)
    val favoriteYear = view.findViewById<TextView>(R.id.tvFavoriteYear)
    val favoriteTime = view.findViewById<TextView>(R.id.tvFavoriteTime)
    val favoriteDelete = view.findViewById<ImageView>(R.id.ivFavoriteDelete)
    val favoriteImage = view.findViewById<ImageView>(R.id.ivFavorite)*/



    fun render(favoritePelisModel: favoritePelis){
        //favoriteName.text = favoritePelisModel.name
        //favoriteYear.text = favoritePelisModel.year
        //favoriteTime.text = favoritePelisModel.timeDuraction
        //Glide.with(favoriteImage.context).load(favoritePelisModel.poster).into(favoriteImage)

        //favoriteImage.load(favoritePelisModel.poster)
        bindin.tvFavoriteName.text = favoritePelisModel.name
        bindin.tvFavoriteYear.text = favoritePelisModel.year
        bindin.tvFavoriteTime.text = favoritePelisModel.timeDuraction
        bindin.ivFavorite.load(favoritePelisModel.poster)
       // println(favoritePelisModel.poster)
    }
}
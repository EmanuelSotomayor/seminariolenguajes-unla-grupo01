package com.example.pelisapp.views.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisapp.models.FavoriteFilmModel
import coil.load
import com.example.pelisapp.databinding.ItemFavoritepelisBinding

class FavoriteFilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val bindin= ItemFavoritepelisBinding.bind(view)

 /*  val favoriteName = view.findViewById<TextView>(R.id.tvFavoriteName)
    val favoriteYear = view.findViewById<TextView>(R.id.tvFavoriteYear)
    val favoriteTime = view.findViewById<TextView>(R.id.tvFavoriteTime)
    val favoriteDelete = view.findViewById<ImageView>(R.id.ivFavoriteDelete)
    val favoriteImage = view.findViewById<ImageView>(R.id.ivFavorite)*/



    fun render(favoriteFilmModelModel: FavoriteFilmModel){
        //favoriteName.text = favoritePelisModel.name
        //favoriteYear.text = favoritePelisModel.year
        //favoriteTime.text = favoritePelisModel.timeDuraction
        //Glide.with(favoriteImage.context).load(favoritePelisModel.poster).into(favoriteImage)

        //favoriteImage.load(favoritePelisModel.poster)
        bindin.tvFavoriteName.text = favoriteFilmModelModel.name
        bindin.tvFavoriteYear.text = favoriteFilmModelModel.year
        bindin.tvFavoriteTime.text = favoriteFilmModelModel.timeDuraction
        bindin.ivFavorite.load(favoriteFilmModelModel.poster)
       // println(favoritePelisModel.poster)
    }
}
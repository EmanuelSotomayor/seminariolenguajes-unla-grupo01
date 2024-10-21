package com.example.pelisapp.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisapp.R
import com.example.pelisapp.models.FilmModel
import com.squareup.picasso.Picasso

class FilmRecyclerViewAdapter(
    private val context: Context,
    private val filmModels: MutableList<FilmModel>
) : RecyclerView.Adapter<FilmRecyclerViewAdapter.MyViewHolder>(), View.OnClickListener {

    private var listener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_view_row, parent, false)
        view.setOnClickListener(this)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val film = filmModels[position]
        holder.filmName.text = film.title
        holder.filmInfo.text = film.info
        Picasso.get().load(film.image)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return filmModels.size
    }

    fun addFilms(newFilms: List<FilmModel>) {
        val startPosition = filmModels.size
        filmModels.addAll(newFilms)
        notifyItemRangeInserted(startPosition, newFilms.size)
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onClick(view: View) {
        listener?.onClick(view)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val filmName: TextView = itemView.findViewById(R.id.filmName)
        val filmInfo: TextView = itemView.findViewById(R.id.filmDescription)
    }

}

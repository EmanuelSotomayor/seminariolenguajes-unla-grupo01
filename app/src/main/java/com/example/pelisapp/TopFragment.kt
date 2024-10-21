package com.example.pelisapp

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import coil.load

class TopFragment :Fragment() {

    private lateinit var titleView: TextView
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View? {
        val view = inflater.inflate(R.layout.fragment_top, container, false)
        titleView = view.findViewById(R.id.titleView)
        imageView = view.findViewById(R.id.imageView)

        // Verifica si los argumentos tienen un titulo e imagen
        arguments?.let {
            val title = it.getString(ARG_TITLE)
            val imageUrl = it.getString(ARG_IMAGE_RES_ID)

            titleView.text = title
            imageUrl?.let { url ->
                imageView.load(url) // Si usas Coil, por ejemplo
            }
        }
        return view
    }

    // Método para actualizar la descripción después de que la vista del fragmento ha sido creada
    fun setMovieData(title: String, imageResId: Int) {
        if (::titleView.isInitialized && ::imageView.isInitialized) {
            titleView.text = title
            imageView.setImageResource(imageResId)
        }
    }

    // Metodo de clase para crear una nueva instancia del fragmento y pasarle datos
    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_IMAGE_RES_ID = "image_res_id"

        fun newInstance(title: String, imageResId: String): TopFragment {
            val fragment = TopFragment()
            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_IMAGE_RES_ID, imageResId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}

package com.example.pelisapp

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.pelisapp.activities.FavoriteActivity

class BottomFragment : Fragment() {

    private lateinit var descriptionView: TextView
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom, container, false)
        descriptionView = view.findViewById(R.id.descriptionView)
        //saveButton = view.findViewById(R.id.buttonSave)

        // Verifica si los argumentos tienen una descripción
        arguments?.let {
            val description = it.getString(ARG_DESCRIPTION)
            descriptionView.text = description
        }

       /* saveButton.setOnClickListener {
            val intent = Intent(requireContext(), FavoriteActivity::class.java)
            startActivity(intent)
        }*/

        return view
    }

    // Método para actualizar la descripción después de que la vista del fragmento ha sido creada
    fun setDescription(description: String) {
        if (::descriptionView.isInitialized) {
            descriptionView.text = description
        }
    }

    // Metodo de clase para crear una nueva instancia del fragmento y pasarle datos
    companion object {
        private const val ARG_DESCRIPTION = "description"

        fun newInstance(description: String): BottomFragment {
            val fragment = BottomFragment()
            val args = Bundle().apply {
                putString(ARG_DESCRIPTION, description)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
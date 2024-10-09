package com.example.pelisapp.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pelisapp.R
import com.example.pelisapp.databinding.ActivityFavoriteBinding
import com.example.pelisapp.models.FavoriteFilmModel
import com.example.pelisapp.models.FavoriteFilmProvider
import com.example.pelisapp.views.adapters.FavoriteFilmAdapter

class FavoriteActivity : BaseActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    private var favoriteMoviesMutableList: MutableList<FavoriteFilmModel> = FavoriteFilmProvider.favoriteMoviesList.toMutableList()
    private lateinit var adapter: FavoriteFilmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityFavoriteBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        layoutInflater.inflate(R.layout.activity_favorite, findViewById(R.id.activity_content))
        val recyclerView: RecyclerView=findViewById(R.id.recycleFavoritePeli)
        initRecyclerView(recyclerView)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
   private fun initRecyclerView(recyclerView: RecyclerView){

        /*val recyclerView = findViewById<RecyclerView>(R.id.recycleFavoritePeli)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FavoritePelisAdapter(FavoritePelisProvider.favoriteMoviesList)*/
       adapter=FavoriteFilmAdapter(
           favoriteFilmModelList =  favoriteMoviesMutableList,
           onClickListener ={ favoriteFilmMovie -> onItemSelected(favoriteFilmMovie) },
           onClickDelete = {position -> onDeleteItem(position)}
       )

      /* binding.recycleFavoritePeli.layoutManager = LinearLayoutManager(this)
       binding.recycleFavoritePeli.adapter =adapter*/
       recyclerView.layoutManager = LinearLayoutManager(this)
       recyclerView.adapter = adapter
   }
    private fun onDeleteItem(position: Int) {
        favoriteMoviesMutableList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
    private fun onItemSelected(favoriteFilmModel: FavoriteFilmModel){

    }
}
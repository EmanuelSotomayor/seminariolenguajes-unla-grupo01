package com.example.pelisapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pelisapp.R;
import com.example.pelisapp.models.FilmModel;

import java.util.ArrayList;

public class FilmRecyclerViewAdapter extends RecyclerView.Adapter<FilmRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener{

    Context context;
    ArrayList<FilmModel> filmModels;
    private View.OnClickListener listener;

    public FilmRecyclerViewAdapter(Context context, ArrayList<FilmModel> filmModels){
        this.context = context;
        this.filmModels = filmModels;
    }

    @NonNull
    @Override
    public FilmRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        view.setOnClickListener(this);
        return new FilmRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.filmName.setText(filmModels.get(position).getTitle());
        holder.filmInfo.setText(filmModels.get(position).getInfo());
        holder.imageView.setImageResource(filmModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return filmModels.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView filmName, filmInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            filmName = itemView.findViewById(R.id.filmName);
            filmInfo = itemView.findViewById(R.id.filmDescription);
        }
    }
}
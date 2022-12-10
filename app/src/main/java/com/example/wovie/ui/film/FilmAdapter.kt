package com.example.wovie.ui.film

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wovie.databinding.CardItemBinding
import com.example.wovie.ui.model.Film

class FilmAdapter(private val films: MutableList<Film>) :
    RecyclerView.Adapter<FilmCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmCardViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmCardViewHolder, position: Int) {
        val actor = films[position]
        holder.bindData(actor)
    }

    override fun getItemCount(): Int {
        return films.size
    }
}
package com.example.wovie.ui.film

import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.wovie.databinding.CardItemBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.wovie.ui.model.Film
import com.example.wovie.util.loadImage

class FilmCardViewHolder(
    private val binding: CardItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(film: Film) {
        film.poster?.let { loadImage(binding.poster.context, it, binding.poster) }
        binding.title.text = film.title
        binding.root.setOnClickListener {
            val action: NavDirections =
                FilmFragmentDirections.actionFilmFragmentToFilmFragment(film)
            Navigation.findNavController(binding.root).navigate(action)
        }
    }
}
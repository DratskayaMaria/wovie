package com.example.wovie.ui.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wovie.R
import com.example.wovie.databinding.FilmItemBinding
import com.example.wovie.ui.main.OnFilmClickListener
import com.example.wovie.ui.model.Film
import com.example.wovie.util.loadImage

class FilmViewHolder(
    private val binding: FilmItemBinding,
    private val listener: OnFilmClickListener?
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindData(film: Film) {
        film.poster?.let { loadImage(binding.poster.context, it, binding.poster) }
        binding.title.text = film.title

        if (film.isBookmarked) {
            binding.bookMark.setImageDrawable(
                ContextCompat.getDrawable(binding.bookMark.context, R.drawable.bookmark)
            )
        } else {
            binding.bookMark.setImageDrawable(
                ContextCompat.getDrawable(binding.bookMark.context, R.drawable.empty_bookmark)
            )
        }
        binding.rating.text = film.rating.toString()

        binding.poster.setOnClickListener {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener?.onItemClick(film)
            }
        }
        binding.bookMark.setOnClickListener {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener?.onBookMarkClicked(film, position)
            }
        }
    }
}

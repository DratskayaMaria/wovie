package com.example.wovie.ui.search

import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.wovie.databinding.FragmentSearchItemBinding
import com.example.wovie.ui.main.MainFragmentDirections
import com.example.wovie.ui.model.Film
import com.example.wovie.util.loadImage

class SearchViewHolder(private val binding: FragmentSearchItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(film: Film?) {
        if (film != null) {
            film.poster?.let {
                loadImage(binding.root.context,
                    it, binding.resultImage)
            }
            binding.rating.text = film.rating.toString()
            binding.title.text = film.title
        }
    }
}
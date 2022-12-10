package com.example.wovie.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.wovie.databinding.FilmItemBinding
import com.example.wovie.ui.OnBookmarkClickListener
import com.example.wovie.ui.model.Film
import com.example.wovie.ui.common.FilmViewHolder


class MainAdapter(

    private val films: MutableList<Film>,
    private val viewModel: MainViewModel,
    private val context: Context,
    private val onBookmarkClickListener: OnBookmarkClickListener

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding, object : OnFilmClickListener {
            override fun onItemClick(film: Film?) {
                film?.let {
                    val action: NavDirections = MainFragmentDirections.actionMainFragmentToFilmFragment(film)
                    Navigation.findNavController(binding.root).navigate(action)
                }
            }

            override fun onBookMarkClicked(film: Film?, position: Int) {
                if (film != null) {
                    films[position] = film
                    viewModel.setBookMarkStatus(film)
                    notifyItemChanged(position)
                    onBookmarkClickListener.setBookmarkedForFilm(film.filmId)
                }
            }
        })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION && holder is FilmViewHolder) {
            val film = films[position]
            holder.bindData(film)
        }
    }

    override fun getItemCount(): Int = films.size
}
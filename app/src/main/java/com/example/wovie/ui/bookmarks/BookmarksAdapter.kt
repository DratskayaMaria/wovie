package com.example.wovie.ui.bookmarks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.wovie.databinding.FilmItemBinding
import com.example.wovie.ui.main.MainFragmentDirections
import com.example.wovie.ui.main.OnFilmClickListener
import com.example.wovie.ui.model.Film
import com.example.wovie.ui.main.FilmViewHolder


class BookmarksAdapter(
    private val bookmarkedFilms: MutableList<Film>,
    private val viewModel: BookmarksViewModel,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding, object : OnFilmClickListener {
            override fun onItemClick(film: Film?) {
                film?.let {
                    val action: NavDirections =
                        BookmarksFragmentDirections.actionBookMarksFragmentToFilmFragment(film)
                    Navigation.findNavController(binding.root).navigate(action)
                }
            }

            override fun onBookMarkClicked(film: Film?, position: Int) {
                if (film != null) {
                    bookmarkedFilms[position] = film
                    viewModel.setBookMarkStatus(film)
                    notifyItemChanged(position)
                }
            }

        })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION && holder is FilmViewHolder) {
            val film = bookmarkedFilms[position]
            holder.bindData(film)
        }
    }

    override fun getItemCount(): Int = bookmarkedFilms.size
}

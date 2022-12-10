package com.example.wovie.ui.film

import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.wovie.databinding.CardItemBinding
import com.example.wovie.ui.model.Actor
import com.example.wovie.util.loadImage

class ActorCardViewHolder(
    private val binding: CardItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(actor: Actor) {
        actor.photo?.let { loadImage(binding.poster.context, it, binding.poster) }
        binding.title.text = actor.name
        binding.root.setOnClickListener {
            val action: NavDirections =
                FilmFragmentDirections.actionFilmFragmentToActorFragment(actor)
            Navigation.findNavController(binding.root).navigate(action)
        }
    }
}
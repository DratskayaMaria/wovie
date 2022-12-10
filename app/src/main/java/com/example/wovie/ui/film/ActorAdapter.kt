package com.example.wovie.ui.film

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wovie.databinding.CardItemBinding
import com.example.wovie.ui.model.Actor

class ActorAdapter(
    private val actors: MutableList<Actor>
) : RecyclerView.Adapter<ActorCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorCardViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorCardViewHolder, position: Int) {
        val actor = actors[position]
        holder.bindData(actor)
    }

    override fun getItemCount(): Int {
        return actors.size
    }
}
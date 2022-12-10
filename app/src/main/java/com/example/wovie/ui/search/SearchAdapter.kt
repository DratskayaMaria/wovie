package com.example.wovie.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wovie.databinding.FragmentSearchItemBinding
import com.example.wovie.ui.model.Film


class SearchAdapter(private val listener: OnSearchResultClickListener?,
                    private val results: List<Film>
) : RecyclerView.Adapter<SearchViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = FragmentSearchItemBinding.inflate(LayoutInflater.from(parent.context))
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bindData(results[position])
            holder.itemView.setOnClickListener {
                listener?.onResultClick(
                    results[position]
                )
            }
        }
    }

    interface OnSearchResultClickListener {
        fun onResultClick(film: Film)
    }

    override fun getItemCount(): Int {
        return results.size
    }
}
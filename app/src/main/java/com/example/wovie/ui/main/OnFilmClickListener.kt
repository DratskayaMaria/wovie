package com.example.wovie.ui.main

import com.example.wovie.ui.model.Film

interface OnFilmClickListener {
    fun onItemClick(film: Film?)
    fun onBookMarkClicked(film: Film?, position: Int)
}
package com.example.wovie.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.wovie.R
import com.example.wovie.api.response.FilmResponse
import com.example.wovie.ui.model.Film

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun loadImage(context: Context, url: String, imageView: ImageView) {
    Glide.with(context)
        .load(IMAGE_BASE_URL + url)
        .error(R.drawable.warning_sign)
        .into(imageView)
}

fun FilmResponse.toFilm(isBookmarked: Boolean = false): Film = Film(
    filmId = this.id,
    poster = this.poster_path,
    cover = this.backdrop_path,
    genres = this.genre_ids,
    rating = this.vote_average,
    countFeedbacks = this.vote_count,
    description = this.overview,
    title = this.title,
    isBookmarked = isBookmarked
)
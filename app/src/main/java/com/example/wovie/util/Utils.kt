package com.example.wovie.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.wovie.R

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun loadImage(context: Context, url: String, imageView: ImageView) {
    Glide.with(context)
        .load(IMAGE_BASE_URL + url)
        .error(R.drawable.warning_sign)
        .into(imageView)
}

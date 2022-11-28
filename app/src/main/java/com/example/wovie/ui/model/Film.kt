package com.example.wovie.ui.model

data class Film (
    val filmId: Int,
    val poster: String?,
    val cover: String?,
    val genres: List<Int>,
    val rating: Double,
    val countFeedbacks: Int,
    val description: String,
    val title: String
)


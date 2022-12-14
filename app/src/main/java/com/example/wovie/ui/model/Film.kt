package com.example.wovie.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film (
    val filmId: Int,
    val poster: String?,
    val cover: String?,
    val genres: List<Int>?,
    val rating: Double,
    val countFeedbacks: Int,
    val description: String,
    val title: String,
    val date: String?,
    var isBookmarked: Boolean = false
) : Parcelable

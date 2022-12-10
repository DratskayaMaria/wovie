package com.example.wovie.api.response

import java.io.Serializable

data class GenreResponse(
    val genres: List<Genre>
) : Serializable
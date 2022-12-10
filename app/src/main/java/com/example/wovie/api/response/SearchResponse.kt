package com.example.wovie.api.response

import com.example.wovie.ui.model.Film

data class SearchResponse (
    var page: Int = 0,
    var results: List<FilmResponse>? = null,
    var total_pages: Int = 0,
    var total_results: Int = 0,
)
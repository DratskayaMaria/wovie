package com.example.wovie.api.response

data class RecommendedResponse(
    var page: Int = 0,
    var results: List<FilmResponse>? = null,
    var total_pages: Int = 0,
    var total_results: Int = 0
)
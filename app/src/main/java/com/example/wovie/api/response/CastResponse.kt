package com.example.wovie.api.response

import java.io.Serializable

data class CastResponse(
    val id: Int,
    val cast: List<ActorResponse>
) : Serializable

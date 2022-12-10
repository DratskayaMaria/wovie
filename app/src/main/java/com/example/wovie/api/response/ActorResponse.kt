package com.example.wovie.api.response

import java.io.Serializable

data class ActorResponse(
    val id: Int,
    val name: String,
    val birthday: String?,
    val deathday: String?,
    val biography: String?,
    val profile_path: String?
) : Serializable
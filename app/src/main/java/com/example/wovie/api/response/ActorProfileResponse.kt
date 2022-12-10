package com.example.wovie.api.response

import java.io.Serializable

data class ActorProfileResponse(
    val id: Int,
    val file_path: String?
) : Serializable
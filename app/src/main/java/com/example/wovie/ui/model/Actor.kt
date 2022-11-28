package com.example.wovie.ui.model

data class Actor(
    val actorId: Int,
    val name: String,
    val dateBirth: String?,
    val dateDeath: String?,
    val bio: String,
    val photo: String?
)

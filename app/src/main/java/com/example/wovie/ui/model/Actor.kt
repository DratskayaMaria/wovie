package com.example.wovie.ui.model

import android.os.Parcelable
import com.example.wovie.api.response.ActorProfileResponse
import com.example.wovie.api.response.ActorResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actor(
    val actorId: Int,
    val name: String,
    val dateBirth: String?,
    val dateDeath: String?,
    val bio: String?,
    val photo: String?
) : Parcelable {

}
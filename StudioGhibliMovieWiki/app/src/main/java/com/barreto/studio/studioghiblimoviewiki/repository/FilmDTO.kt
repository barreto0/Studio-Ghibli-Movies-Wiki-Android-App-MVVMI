package com.barreto.studio.studioghiblimoviewiki.repository

import com.google.gson.annotations.SerializedName


data class FilmDTO (
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    val director: String? = null
)

data class FilmListDTO(

    val results: Array<FilmDTO>? = null
)
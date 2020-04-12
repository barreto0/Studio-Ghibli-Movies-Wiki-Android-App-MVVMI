package com.barreto.studio.studioghiblimoviewiki.domain

data class Film (
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val releaseDate: String? = null,
    val director: String? = null
)

data class FilmList(

    val results: Array<Film>? = null
)
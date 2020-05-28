package com.barreto.studio.studioghiblimoviewiki.domain

import java.io.Serializable

data class Film (
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val releaseDate: String? = null,
    val director: String? = null
): Serializable
{
    constructor():this("","","","",""){

    }
}

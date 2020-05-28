package com.barreto.studio.studioghiblimoviewiki.domain

data class Profile(
val Nome: String,
val Email: String,
val Senha: String,
val Latitude: String,
val Longitude: String
){
    constructor():this("","","","",""){

    }
}

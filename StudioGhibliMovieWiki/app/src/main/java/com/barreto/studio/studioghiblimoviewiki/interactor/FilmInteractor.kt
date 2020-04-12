package com.barreto.studio.studioghiblimoviewiki.interactor

import android.content.Context
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.repository.FilmsRepository

class FilmInteractor (private val context: Context) {
    private val filmRepository = FilmsRepository(context,"https://ghibliapi.herokuapp.com/")

    fun getFilmsList(callback: (movies: Array<Film>) -> Unit){
        filmRepository.getFilmsList(callback)
    }
}
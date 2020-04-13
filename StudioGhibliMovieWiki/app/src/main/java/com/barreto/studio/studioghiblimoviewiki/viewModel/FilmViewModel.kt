package com.barreto.studio.studioghiblimoviewiki.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.interactor.FilmInteractor

class FilmViewModel (val app: Application) : AndroidViewModel(app) {
    private val interactor = FilmInteractor(app.applicationContext)

    val result = MutableLiveData<Array<Film>>()

    fun getFilmsList(){
        interactor.getFilmsList {film->
            result.value = film
        }
    }
}
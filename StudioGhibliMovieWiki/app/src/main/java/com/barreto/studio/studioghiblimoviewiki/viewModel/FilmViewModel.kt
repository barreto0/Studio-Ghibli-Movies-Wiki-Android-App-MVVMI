package com.barreto.studio.studioghiblimoviewiki.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.interactor.FilmInteractor
import kotlinx.android.synthetic.main.list_movie.view.*

class FilmViewModel (val app: Application) : AndroidViewModel(app) {
    private val interactor = FilmInteractor(app.applicationContext)

    val result = MutableLiveData<Array<Film>>()

    fun getFilmsList(){
        interactor.getFilmsList { films ->
            val filmes = mutableListOf<Film>()

            // Pelo fato da responsabilidade de transformar um dado para apresentação ser das "ViewModels"
            // as linha abaixo modificam os dados antes de serem apresentados para o usuário.
            // Neste caso será acrecentado a URL Base da API The Movie DB para recuperar imagens
            // de um filme criando um novo objeto Movie com essa modificação
            films.forEach { f ->
                val newMovie = Film(
                    id = f.id,
                    title = f.title,
                    description = f.description,
                    director = f.director,
                    releaseDate = f.releaseDate
                )
                filmes.add(newMovie)
            }

            result.value = filmes.toTypedArray()
        }
    }
}

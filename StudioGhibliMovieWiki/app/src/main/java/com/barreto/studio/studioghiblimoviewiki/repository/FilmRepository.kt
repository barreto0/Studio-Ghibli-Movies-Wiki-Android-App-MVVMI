package com.barreto.studio.studioghiblimoviewiki.repository

import android.content.Context
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmsService{

    @GET("films")
    fun getFilmsList(): Call<FilmListDTO>

//    @GET("films")
//    fun getFilmById(
//        @Path("movieId") filmId: String
//    ): Call<FilmDTO>

}




class FilmsRepository(context: Context, baseUrl: String) : BaseRetrofit(context, baseUrl) {
    private val service = retrofit.create(FilmsService::class.java)

    fun getFilmsList(callback: (films: Array<Film>)->Unit){

        service.getFilmsList().enqueue(object : Callback<FilmListDTO>{
            override fun onResponse(call: Call<FilmListDTO>, response: Response<FilmListDTO>) {
                val result = mutableListOf<Film>()
                val films = response.body()?.results

                films?.forEach { f->

                    val domain = Film(
                        id = f.id,
                        title = f.title,
                        description = f.description,
                        director = f.director,
                        releaseDate = f.releaseDate

                    )

                    result.add(domain)
                }


                callback(result.toTypedArray())
            }

            override fun onFailure(call: Call<FilmListDTO>, error: Throwable) {
                callback(arrayOf())
            }
        })
    }
}
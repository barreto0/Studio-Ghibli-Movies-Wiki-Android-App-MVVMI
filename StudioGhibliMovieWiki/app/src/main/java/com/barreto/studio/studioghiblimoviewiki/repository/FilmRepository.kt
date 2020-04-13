package com.barreto.studio.studioghiblimoviewiki.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmsService{

    @GET("films")
    fun getFilmsList(): Call<List<FilmDTO>>
    //fun getFilmsList(): Call<FilmListDTO>

//    @GET("films")
//    fun getFilmById(
//        @Path("movieId") filmId: String
//    ): Call<FilmDTO>

}




class FilmsRepository(context: Context, baseUrl: String) : BaseRetrofit(context, baseUrl) {
    private val service = retrofit.create(FilmsService::class.java)

    fun getFilmsList(callback: (films: Array<Film>)->Unit){

        service.getFilmsList().enqueue(object : Callback<List<FilmDTO>>{
            override fun onResponse(call: Call<List<FilmDTO>>, response: Response<List<FilmDTO>>) {
                val result = mutableListOf<Film>()
                val films = response.body()

                films?.forEach { f->

                    val domain = Film(
                        id = f.id,
                        title = f.title,
                        description = f.description,
                        director = f.director,
                        releaseDate = f.releaseDate
                    )
                    Log.d("film","PORRA")
                    result.add(domain)
                }



                callback(result.toTypedArray())
            }

            override fun onFailure(call: Call<List<FilmDTO>>, t: Throwable) {
                callback(arrayOf()) //array vazio p n quebrar o app
            }
        })
    }
}
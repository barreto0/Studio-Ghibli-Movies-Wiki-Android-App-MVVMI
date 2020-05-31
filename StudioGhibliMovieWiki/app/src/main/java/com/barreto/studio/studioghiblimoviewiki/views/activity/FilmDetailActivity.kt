package com.barreto.studio.studioghiblimoviewiki.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.repository.UserRepository
import com.barreto.studio.studioghiblimoviewiki.viewModel.UserViewModel
import kotlinx.android.synthetic.main.activity_film_detail.*
import kotlinx.android.synthetic.main.film_list_item.*

class FilmDetailActivity : AppCompatActivity() {

    val userRepository = UserRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_detail)


        val film = intent.extras?.get("film") as Film
        val title: String? = film.title
        val description: String? = film.description
        val releaseDate: String? = film.releaseDate
        val director: String? = film.director
        val id: String? = film.id

        //        val title: String? = intent.getStringExtra("title")
//        val description: String? = intent.getStringExtra("description")
//        val releaseDate: String? = intent.getStringExtra("releaseDate")
//        val director: String? = intent.getStringExtra("director")

        tvTitleDetail.text = title
        tvDescriptionDetail.text = description
        tvReleaseDateDetail.text = releaseDate
        tvDirectorDetail.text = director

        btnAddFavorite.setOnClickListener {addFilmToUserFavorites(film)}
        btnRemoveFavorite.setOnClickListener{removeFilmUserFavorites(id!!)}
    }

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    fun addFilmToUserFavorites(favorite: Film){
        viewModel.addFilmToUserFavorites(favorite)
        viewModel.result.observe(this, Observer {
            if (it){
                Toast.makeText(this,"Adicionado aos favoritos com sucesso!",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Você já adicionou esse filme!",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun removeFilmUserFavorites(id: String){
        viewModel.removeFilmUserFavorites(id)
        viewModel.result.observe(this, Observer {
            if (it){
                Toast.makeText(this,"Filme removido dos favoritos com sucesso!",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Não foi possível remover esse filme",Toast.LENGTH_SHORT).show()
            }
        })
    }
}

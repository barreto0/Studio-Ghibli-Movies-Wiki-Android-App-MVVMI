package com.barreto.studio.studioghiblimoviewiki.views.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.repository.UserRepository
import com.barreto.studio.studioghiblimoviewiki.viewModel.UserViewModel
import com.barreto.studio.studioghiblimoviewiki.views.adapter.FilmAdapter
import com.barreto.studio.studioghiblimoviewiki.views.adapter.OnFilmItemClickListener
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.Serializable

class ProfileActivity : AppCompatActivity(), OnFilmItemClickListener {

    val userRepository = UserRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        configureRecyclerView()
        showFilmFavorites()
    }

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    private fun configureRecyclerView(){
        rvFavorites.layoutManager = LinearLayoutManager(this) //falando para a atividade mostrar os dados de forma linear vertical
    }

    private fun showFilmFavorites(){

        viewModel.resultGetFavoritesFromUser.observe(this, Observer {films->
            //tvDebug.text = films.contentToString()
            val adapter = FilmAdapter(films,this)
            rvFavorites.adapter = adapter

        })
        viewModel.retrieveFavoritesFromUser()

    }

    override fun onItemClick(item: Film, position: Int) {
        val intentFilmDetail = Intent(this,
            FilmDetailActivity::class.java)
//        intentFilmDetail.putExtra("title",item.title)
//        intentFilmDetail.putExtra("description",item.description)
//        intentFilmDetail.putExtra("releaseDate", item.releaseDate)
//        intentFilmDetail.putExtra("director", item.director)
        intentFilmDetail.putExtra("film",item as Serializable)
        startActivity(intentFilmDetail)
    }
}

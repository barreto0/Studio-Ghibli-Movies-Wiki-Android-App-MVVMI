package com.barreto.studio.studioghiblimoviewiki.views.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.viewModel.UserViewModel
import com.barreto.studio.studioghiblimoviewiki.views.adapter.FilmAdapter
import com.barreto.studio.studioghiblimoviewiki.views.adapter.OnFilmItemClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.Serializable

class ProfileActivity : AppCompatActivity(), OnFilmItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        title = "Meus Favoritos"
        changeProfileTitle()
        configureRecyclerView()
        showFilmFavorites()

    }

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    private fun configureRecyclerView(){
        rvFavorites.layoutManager = LinearLayoutManager(this) //falando para a atividade mostrar os dados de forma linear vertical
    }

    private fun changeProfileTitle(){
        viewModel.getCurrentUserData()
        viewModel.username.observe(this, Observer {
            //title = "Filmes favoritos do $it"
        })
    }

    private fun showFilmFavorites(){

        viewModel.resultGetFavoritesFromUser.observe(this, Observer {
            val adapter = FilmAdapter(it,this)
            rvFavorites.adapter = adapter
            if (it.isEmpty()){
                tvEmpty.text = "Está bem vazio por aqui não é? Tente adicionar os filmes que mais gosta aos favoritos!"
            }

        })
        viewModel.retrieveFavoritesFromUser()

    }

    override fun onItemClick(item: Film, position: Int) {
        val intentFilmDetail = Intent(this, FilmDetailActivity::class.java)
        intentFilmDetail.putExtra("film",item as Serializable)
        startActivity(intentFilmDetail)
    }
}

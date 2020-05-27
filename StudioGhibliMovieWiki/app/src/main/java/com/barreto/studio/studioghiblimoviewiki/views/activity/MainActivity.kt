package com.barreto.studio.studioghiblimoviewiki.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.viewModel.FilmViewModel
import com.barreto.studio.studioghiblimoviewiki.views.adapter.FilmAdapter
import com.barreto.studio.studioghiblimoviewiki.views.adapter.OnFilmItemClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnFilmItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //btnTesteApi.setOnClickListener { showFilms() }

        configureRecyclerView()
        showFilms()


    }

    private val viewModel: FilmViewModel by lazy {
        ViewModelProvider(this).get(FilmViewModel::class.java)
    }

    private fun configureRecyclerView(){
        rvFilms.layoutManager = LinearLayoutManager(this) //falando para a atividade mostrar os dados de forma linear vertical
    }

    private fun showFilms(){

        viewModel.result.observe(this, Observer {films->
            //tvDebug.text = films.contentToString()
            val adapter = FilmAdapter(films,this)
            rvFilms.adapter = adapter
            while (films==null || films.isEmpty()){
                progressBarMain.visibility = View.VISIBLE
            }
            progressBarMain.visibility = View.GONE
        })
        viewModel.getFilmsList()

    }

    override fun onItemClick(item: Film, position: Int) {
        val intentFilmDetail = Intent(this,
            FilmDetailActivity::class.java)
        intentFilmDetail.putExtra("title",item.title)
        intentFilmDetail.putExtra("description",item.description)
        intentFilmDetail.putExtra("releaseDate", item.releaseDate)
        intentFilmDetail.putExtra("director", item.director)
        startActivity(intentFilmDetail)
    }
}

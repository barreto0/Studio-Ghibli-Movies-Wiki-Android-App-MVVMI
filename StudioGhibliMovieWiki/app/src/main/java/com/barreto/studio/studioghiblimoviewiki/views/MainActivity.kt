package com.barreto.studio.studioghiblimoviewiki.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.viewModel.FilmViewModel
import com.barreto.studio.studioghiblimoviewiki.views.adapter.FilmAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
            val adapter = FilmAdapter(films)
            rvFilms.adapter = adapter
            while (films==null || films.isEmpty()){
                progressBarMain.visibility = View.VISIBLE
            }
            progressBarMain.visibility = View.GONE
        })
        viewModel.getFilmsList()

    }
}

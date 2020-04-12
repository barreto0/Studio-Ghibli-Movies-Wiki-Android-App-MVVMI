package com.barreto.studio.studioghiblimoviewiki.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.viewModel.FilmViewModel
import com.barreto.studio.studioghiblimoviewiki.views.adapter.FilmAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureRecyclerView()
        showfilms()
    }

    private val viewModel: FilmViewModel by lazy {
        ViewModelProvider(this).get(FilmViewModel::class.java)
    }

    private fun showFilms(){
        viewModel.getFilmsList()
    }
    private fun configureRecyclerView() {
        recyclefilm.layoutManager = LinearLayoutManager(this)
    }

    private fun showfilms() {
        viewModel.result.observe(this, Observer { films ->
            val adapter = FilmAdapter(films)
            recyclefilm.adapter = adapter
        })

        viewModel.getFilmsList()
    }
}

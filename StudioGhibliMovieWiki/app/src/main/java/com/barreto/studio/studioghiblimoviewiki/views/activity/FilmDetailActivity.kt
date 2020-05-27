package com.barreto.studio.studioghiblimoviewiki.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.barreto.studio.studioghiblimoviewiki.R
import kotlinx.android.synthetic.main.activity_film_detail.*
import kotlinx.android.synthetic.main.film_list_item.*

class FilmDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_detail)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val title: String? = intent.getStringExtra("title")
        val description: String? = intent.getStringExtra("description")
        val releaseDate: String? = intent.getStringExtra("releaseDate")
        val director: String? = intent.getStringExtra("director")

        tvTitleDetail.text = title
        tvDescriptionDetail.text = description
        tvReleaseDateDetail.text = releaseDate
        tvDirectorDetail.text = director
    }
}

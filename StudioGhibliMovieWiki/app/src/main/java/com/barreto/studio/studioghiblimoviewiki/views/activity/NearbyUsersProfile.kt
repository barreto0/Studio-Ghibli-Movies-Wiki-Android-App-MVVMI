package com.barreto.studio.studioghiblimoviewiki.views.activity

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.viewModel.UserViewModel
import com.barreto.studio.studioghiblimoviewiki.views.adapter.FilmAdapter
import com.barreto.studio.studioghiblimoviewiki.views.adapter.OnFilmItemClickListener
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_nearby_users_profile.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.Serializable

class NearbyUsersProfile : AppCompatActivity(), OnFilmItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_users_profile)
        val loc = intent.extras?.get("location") as LatLng
        configureRecyclerView()
        getData(loc)
    }
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    private fun configureRecyclerView(){
        rvNearbyFavorites.layoutManager = LinearLayoutManager(this) //falando para a atividade mostrar os dados de forma linear vertical
    }

    override fun onItemClick(item: Film, position: Int) {
        val intentFilmDetail = Intent(this, FilmDetailActivity::class.java)
        intentFilmDetail.putExtra("film",item as Serializable)
        startActivity(intentFilmDetail)
    }

    fun getData(location: LatLng){
        viewModel.resultRetrieveUserFavoritesDataWithLocation.observe(this, Observer {
            val adapter = FilmAdapter(it,this)
            rvNearbyFavorites.adapter = adapter
            if (it.isEmpty()){
                tvEmptyFavorites.text = "Está bem vazio por aqui não é?"
            }
        })
        viewModel.username.observe(this, Observer {
            title = "Favoritos do $it"
        })
        viewModel.retrieveUserDataWithLocation(location)
    }
}

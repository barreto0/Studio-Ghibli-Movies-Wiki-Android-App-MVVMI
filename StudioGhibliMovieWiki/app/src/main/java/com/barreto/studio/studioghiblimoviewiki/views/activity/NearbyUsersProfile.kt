package com.barreto.studio.studioghiblimoviewiki.views.activity

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.viewModel.UserViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_nearby_users_profile.*

class NearbyUsersProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_users_profile)
        val loc = intent.extras?.get("location") as LatLng
        tvNbu.text = loc.toString()
        getData(loc)
    }
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    fun getData(location: LatLng){
        viewModel.retrieveUserDataWithLocation(location)
    }
}

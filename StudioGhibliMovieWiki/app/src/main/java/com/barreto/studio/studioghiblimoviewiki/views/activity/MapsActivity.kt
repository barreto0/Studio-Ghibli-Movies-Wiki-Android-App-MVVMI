package com.barreto.studio.studioghiblimoviewiki.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.barreto.studio.studioghiblimoviewiki.R
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.barreto.studio.studioghiblimoviewiki.domain.Profile
import com.barreto.studio.studioghiblimoviewiki.repository.UserRepository
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback{

    val PERMISSION_ID = 69
    private lateinit var mMap: GoogleMap
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    lateinit var userList: MutableList<Profile>


    private val userRepository =
        UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        userList = userRepository.retrieveUserData()
        //getLastLocation()
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getlastLocation(mMap: GoogleMap) {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {

                        userRepository.updateCurrentUserLocation(location.latitude.toString(), location.longitude.toString())

                        for(u in userList){
                            var userLocation = LatLng(u.Latitude.toDouble(),u.Longitude.toDouble())
                            mMap.addMarker(MarkerOptions().position(userLocation).title(u.Nome).icon(
                                BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(resources, R.mipmap.icon_user_location))))
                        }
                        mMap.setOnInfoWindowClickListener { marker ->
                            val loc = LatLng(marker.position.latitude,marker.position.longitude)

                            val intentNearbyUsersProfile = Intent(this,NearbyUsersProfile::class.java)
                            intentNearbyUsersProfile.putExtra("location",loc)
                            startActivity(intentNearbyUsersProfile)
                        }

                        var currentUserLocation = LatLng(location.latitude,location.longitude)
                       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentUserLocation, 12f))
                    }
                }
            } else {
                Toast.makeText(this, "Ligue o GPS", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }



    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        @SuppressLint("SetTextI18n")
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getlastLocation(mMap)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getlastLocation(mMap)
    }



}

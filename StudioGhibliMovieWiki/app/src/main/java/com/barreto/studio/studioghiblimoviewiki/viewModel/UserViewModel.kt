package com.barreto.studio.studioghiblimoviewiki.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.interactor.UserInteractor
import com.google.android.gms.maps.model.LatLng

class UserViewModel (val app: Application) : AndroidViewModel(app) {

    private val interactor =
        UserInteractor(app.applicationContext)

    val result = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()

    val resultGetFavoritesFromUser = MutableLiveData<Array<Film>>()

    var username = MutableLiveData<String>()

    val resultRetrieveUserFavoritesDataWithLocation = MutableLiveData<Array<Film>>()

    fun login(email: String, senha: String){ //AQUI SÓ PODE TER REGRA DE FORMATAÇAO

        interactor.login(email,senha){
            resultado, mensagem ->
            if (resultado){
                result.value = true

            }else{
                result.value = false
                msg.value = mensagem
            }
        }


    }

    fun register(email: String, senha:String, nome: String, latitude: String, longitude: String ){
        interactor.register(email,senha, nome, latitude, longitude){
                resultado, mensagem ->
            if (resultado){
                result.value = true
                msg.value = mensagem

            }else{
                result.value = false
                msg.value = mensagem
            }

        }

    }

    fun resetPassword(email: String){
        interactor.resetPassword(email){
            result.value = it
        }
    }

    fun addFilmToUserFavorites(favorite: Film){
        interactor.addFilmToUserFavorites(favorite){
            result.value = it
        }
    }

    fun retrieveFavoritesFromUser(){
        interactor.retrieveFavoritesFromUser(){
            resultGetFavoritesFromUser.value = it
        }

    }

    fun getCurrentUserData(){
        interactor.getCurrentUserData {
            username.value = it.Nome
        }
    }

    fun retrieveUserDataWithLocation(location: LatLng){
        interactor.retrieveUserDataWithLocation(location){nome, films ->
            username.value = nome
            resultRetrieveUserFavoritesDataWithLocation.value = films
        }
    }

}
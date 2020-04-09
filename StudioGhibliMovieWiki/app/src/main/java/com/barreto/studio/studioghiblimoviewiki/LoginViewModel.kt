package com.barreto.studio.studioghiblimoviewiki

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class LoginViewModel (val app: Application) : AndroidViewModel(app) {

    private val interactor = LoginInteractor(app.applicationContext)

    fun login(email: String, senha: String){

        //AQUI SÓ PODE TER REGRA DE FORMATAÇAO

        interactor.login(email,senha){

        }

    }

}
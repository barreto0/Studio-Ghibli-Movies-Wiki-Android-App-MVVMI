package com.barreto.studio.studioghiblimoviewiki

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class LoginViewModel (val app: Application) : AndroidViewModel(app) {

    private val interactor = LoginInteractor(app.applicationContext)

    val resultLogin = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()

    fun login(email: String, senha: String){ //AQUI SÓ PODE TER REGRA DE FORMATAÇAO

        interactor.login(email,senha){
            resultado, mensagem ->
            if (resultado.equals(true)){
                resultLogin.value = true
                msg.value = mensagem
            }else{
                resultLogin.value = false
                msg.value = mensagem
            }
        }


    }

}
package com.barreto.studio.studioghiblimoviewiki.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.barreto.studio.studioghiblimoviewiki.interactor.LoginInteractor

class LoginViewModel (val app: Application) : AndroidViewModel(app) {

    private val interactor =
        LoginInteractor(app.applicationContext)

    val resultLogin = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()

    fun login(email: String, senha: String){ //AQUI SÓ PODE TER REGRA DE FORMATAÇAO

        interactor.login(email,senha){
            resultado, mensagem ->
            if (resultado){
                resultLogin.value = true

            }else{
                resultLogin.value = false
                msg.value = mensagem
            }
        }


    }

}
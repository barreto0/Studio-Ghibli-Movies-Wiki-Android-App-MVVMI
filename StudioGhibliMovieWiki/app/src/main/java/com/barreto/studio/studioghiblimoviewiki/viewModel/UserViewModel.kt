package com.barreto.studio.studioghiblimoviewiki.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.barreto.studio.studioghiblimoviewiki.interactor.UserInteractor

class UserViewModel (val app: Application) : AndroidViewModel(app) {

    private val interactor =
        UserInteractor(app.applicationContext)

    val result = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()

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

    fun register(email: String, senha:String, nome: String){
        interactor.register(email,senha, nome){
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

}
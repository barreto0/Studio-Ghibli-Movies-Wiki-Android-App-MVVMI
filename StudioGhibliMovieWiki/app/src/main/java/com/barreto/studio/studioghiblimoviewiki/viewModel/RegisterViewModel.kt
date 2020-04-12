package com.barreto.studio.studioghiblimoviewiki.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.barreto.studio.studioghiblimoviewiki.interactor.RegisterInteractor

class RegisterViewModel(val app: Application) : AndroidViewModel(app) {

    private val interactor = RegisterInteractor(app.applicationContext)

    val resultRegister = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()

    fun register(email: String, senha:String, nome: String){
        interactor.register(email,senha, nome){
                resultado, mensagem ->
            if (resultado){
                resultRegister.value = true
                msg.value = mensagem

            }else{
                resultRegister.value = false
                msg.value = mensagem
            }

        }

    }
}
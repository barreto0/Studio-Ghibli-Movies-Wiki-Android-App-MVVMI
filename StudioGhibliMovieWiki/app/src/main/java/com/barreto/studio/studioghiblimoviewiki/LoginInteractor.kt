package com.barreto.studio.studioghiblimoviewiki

import android.content.Context

class LoginInteractor (private val context: Context) {
    val loginRepository = LoginRepository(context)

    fun login(email: String, senha: String, callback: (mensagem: String)-> Unit){

        //AQUI FAZ AS REGRAS DE NEGÓCIO DO LOGIN


    }

}
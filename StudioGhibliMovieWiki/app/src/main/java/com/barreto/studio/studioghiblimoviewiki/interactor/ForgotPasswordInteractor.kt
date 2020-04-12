package com.barreto.studio.studioghiblimoviewiki.interactor

import android.content.Context
import com.barreto.studio.studioghiblimoviewiki.repository.UserRepository

class ForgotPasswordInteractor (private val context: Context) {

    val userRepository = UserRepository()

    fun resetPassword(email: String, callback: (resultado: Boolean)->Unit){
        //valida email
        if(email.isEmpty()){
            callback(false)
            return
        }

        userRepository.resetPassword(email){
            callback(it)
        }
    }
}
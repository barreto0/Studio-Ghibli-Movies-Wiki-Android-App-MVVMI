package com.barreto.studio.studioghiblimoviewiki.interactor

import android.content.Context
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.domain.Profile
import com.barreto.studio.studioghiblimoviewiki.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserInteractor (private val context: Context) {
    val userRepository =
        UserRepository()


    fun login(email: String, senha: String, callback: (resultado: Boolean, mensagem: String?)-> Unit){

        //AQUI FAZ AS REGRAS DE NEGÓCIO DO LOGIN, VALIDAÇOES
        if (email.isEmpty() && senha.isNotEmpty()) {
            callback(false, "E-mail obrigatório!")
            return
        }

        if (senha.isEmpty() && email.isNotEmpty()) {
            callback(false,"Senha obrigatória!")
            return
        }

        if (email.isEmpty() && senha.isEmpty()) {
            callback(false,"Preencha os campos corretamente")
            return
        }

        if(senha.length < 6){
            callback(false,null)
            return
        }


        userRepository.authLogin(email,senha){
            resultado, mensagem -> callback(resultado, mensagem)
        }

    }

    fun register(email: String, senha: String, nome:String,latitude: String, longitude: String, callback: (resultado: Boolean, mensagem: String?)-> Unit){

        //AQUI FAZ AS REGRAS DE NEGÓCIO DO LOGIN, VALIDAÇOES
        //TODO: acrescentar validaçao de email sem ser por regex
        if (email.isEmpty() && senha.isNotEmpty()) {
            callback(false, "E-mail obrigatório!")
            return
        }

        if (senha.isEmpty() && email.isNotEmpty()) {
            callback(false,"Senha obrigatória!")
            return
        }

        if (email.isEmpty() && senha.isEmpty()) {
            callback(false,"Preencha os campos corretamente")
            return
        }

        if(senha.length < 6){
            callback(false,"Sua senha precisa ter ao menos 6 caracteres!")
            return
        }

        val profile = Profile(
            nome,
            email,
            senha,
            latitude,
            longitude
        )

        userRepository.createUser(profile){
                resultado, mensagem -> callback(resultado, mensagem)
        }
    }

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

    fun addFilmToUserFavorites(favorite: Film, callback: (resultado: Boolean)->Unit){
        userRepository.addFilmToUserFavorites(favorite){
            callback(it)
        }
    }

    fun retrieveFavoritesFromUser( callback: (films: Array<Film>)->Unit){
        userRepository.retrieveFavoritesFromUser( callback)
    }

}
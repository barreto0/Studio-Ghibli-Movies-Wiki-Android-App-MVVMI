package com.barreto.studio.studioghiblimoviewiki

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginRepository() {

    private val TAG = "DebugFirebase"
    val mAuth = FirebaseAuth.getInstance()

    fun authLogin(email:String, senha:String, callback: (resultado: Boolean,mensagem: String)-> Unit){
        //fazer a autenticaçao e jogar pra tela principal se tiver dado certo

        val operation = mAuth.signInWithEmailAndPassword(email, senha)
        operation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val msg = "sucesso"
                Log.v(TAG, task.toString())
                callback(true,msg)
            } else {
                Log.v(TAG, task.toString())
                val erro = task.exception?.localizedMessage ?: "Deu Ruim"
                callback(false,erro)
            }
        }
    }

}

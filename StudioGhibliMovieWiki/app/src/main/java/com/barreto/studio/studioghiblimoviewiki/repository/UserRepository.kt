package com.barreto.studio.studioghiblimoviewiki.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.barreto.studio.studioghiblimoviewiki.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserRepository() {

    private val TAG = "DebugFirebase"
    val mAuth = FirebaseAuth.getInstance()
    val mDatabase = FirebaseDatabase.getInstance()
    val mDatabaseReference = mDatabase.reference.child("Users")

    fun authLogin(email:String, senha:String, callback: (resultado: Boolean,mensagem: String)-> Unit){
        //fazer a autenticaçao e jogar pra tela principal se tiver dado certo

        val operation = mAuth.signInWithEmailAndPassword(email, senha)
        operation.addOnCompleteListener { task ->
            if (task.isSuccessful) {

                Log.v(TAG, task.toString())
                callback(true,"sucesso")
            } else {
                Log.v(TAG, task.toString())
                val erro = task.exception?.localizedMessage ?: "Deu Ruim"
                callback(false,erro)
            }
        }
    }

    fun createUser(profile: Profile, callback: (resultado: Boolean,mensagem: String)-> Unit){

       val operation = mAuth.createUserWithEmailAndPassword(profile.email, profile.senha)
        operation.addOnCompleteListener{task ->
            if (task.isSuccessful){
                val userId = mAuth.currentUser!!.uid
                val userAtual = mDatabaseReference.child(userId)
                userAtual.child("Nome").setValue(profile.nome)
                userAtual.child("Email").setValue(profile.email)

                callback(true,"sucesso")
            }else{
                val erro = task.exception?.localizedMessage?: "Deu Ruim"
                callback(false,erro)
            }

        }
    }

}

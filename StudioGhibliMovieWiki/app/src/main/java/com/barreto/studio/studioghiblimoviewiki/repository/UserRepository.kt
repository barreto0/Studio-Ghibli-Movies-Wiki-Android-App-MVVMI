package com.barreto.studio.studioghiblimoviewiki.repository

import android.util.Log
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.domain.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepository() {

    private val TAG = "DebugFirebase"
    val mAuth = FirebaseAuth.getInstance()
    val mDatabase = FirebaseDatabase.getInstance()
    val mDatabaseReferenceUsers = mDatabase.reference.child("Users")


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

    fun createUser(profile: Profile, callback: (resultado: Boolean, mensagem: String)-> Unit){

       val operation = mAuth.createUserWithEmailAndPassword(profile.Email, profile.Senha)
        operation.addOnCompleteListener{task ->
            if (task.isSuccessful){
                val userId = mAuth.currentUser!!.uid
                val userAtual = mDatabaseReferenceUsers.child(userId)
                userAtual.child("Nome").setValue(profile.Nome)
                userAtual.child("Email").setValue(profile.Email)
                userAtual.child("Latitude").setValue(profile.Latitude)
                userAtual.child("Longitude").setValue(profile.Longitude)
                callback(true,"sucesso")
            }else{
                val erro = task.exception?.localizedMessage?: "Deu Ruim"
                callback(false,erro)
            }

        }
    }

    fun resetPassword(email: String, callback: (resultado: Boolean) -> Unit){
        val operation = mAuth.sendPasswordResetEmail(email)
        operation.addOnCompleteListener{task ->
            if (task.isSuccessful){
                callback(true)
            }else{
                callback(false)
            }
        }
    }

    fun updateCurrentUserLocation(latitude:String, longitude:String){
        if (mAuth.currentUser!=null){
            println("USUÁRIO ${mAuth.currentUser!!.uid}")
            val userId = mAuth.currentUser!!.uid
            val userAtual = mDatabaseReferenceUsers.child(userId)
            userAtual.child("Latitude").setValue(latitude)
            userAtual.child("Longitude").setValue(longitude)
        }else{
            println("Usuário deslogado")
        }
    }

    fun retrieveUserData(): MutableList<Profile> {

        var userList: MutableList<Profile> = mutableListOf()
        mDatabaseReferenceUsers.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                print("deu ruim")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    //print(p0.value)
                    for (u in p0.children){
                        val user = u.getValue(Profile::class.java)
                        print("USER: $user")
                        userList.add(user!!)
                    }
                }else{
                    print("vazio")
                }
            }
        })
        return userList
    }

    fun addFilmToUserFavorites(favorite: Film, callback: (resultado: Boolean) -> Unit){
        if (mAuth.currentUser!=null){
            println("USUÁRIO ${mAuth.currentUser!!.uid}")
            val userId = mAuth.currentUser!!.uid
            val userAtual = mDatabaseReferenceUsers.child(userId)
            userAtual.child("Favoritos").child("${favorite.id}").setValue(favorite)
            callback(true)
        }else{
            println("Usuário deslogado")
            callback(false)
        }
    }

    fun retrieveFavoritesFromUser(callback: (films: Array<Film>)->Unit){
        var filmList: MutableList<Film> = mutableListOf()
        Log.d("USUARIO ATUAL LOG","CURRENT USER ID ${mAuth.currentUser!!.uid}")
        mDatabaseReferenceUsers.child("${mAuth.currentUser!!.uid}").child("Favoritos").addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                print("deu ruim")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    Log.d("USUARIO ATUAL LOG","conteudo dataSnapshot Favoritos ${p0.value}")
                    for (f in p0.children){
                        val film = f.getValue(Film::class.java)
                        Log.d("USUARIO ATUAL LOG","FILME $film")
                        filmList.add(film!!)
                    }
                }else{
                    print("vazio")
                }
            }
        })
        Log.d("USUARIO ATUAL LOG","Lista de filmes favoritos ${filmList.toTypedArray().contentToString()}")
        callback(filmList.toTypedArray())
    }

}

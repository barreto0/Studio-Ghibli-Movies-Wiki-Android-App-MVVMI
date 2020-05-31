package com.barreto.studio.studioghiblimoviewiki.repository

import android.location.Location
import android.util.Log
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.domain.Profile
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepository() {

    val mAuth = FirebaseAuth.getInstance()
    val mDatabase = FirebaseDatabase.getInstance()
    val mDatabaseReferenceUsers = mDatabase.reference.child("Users")

    fun authLogin(email:String, senha:String, callback: (resultado: Boolean,mensagem: String)-> Unit){
        //fazer a autenticaçao e jogar pra tela principal se tiver dado certo
        val operation = mAuth.signInWithEmailAndPassword(email, senha)
        operation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(true,"sucesso")
            } else {
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

    fun addFilmToUserFavorites(favorite: Film,callback: (resultado: Boolean,mensagem: String) -> Unit){
        val filmList: MutableList<Film> = mutableListOf()
        if (mAuth.currentUser!=null){
            println("USUÁRIO ${mAuth.currentUser!!.uid}")
            val userId = mAuth.currentUser!!.uid
            val userAtual = mDatabaseReferenceUsers.child(userId)
//            userAtual.child("Favoritos").child("${favorite.id}").setValue(favorite)
            mDatabaseReferenceUsers.child(mAuth.currentUser!!.uid).child("Favoritos").addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    print("deu ruim")
                }
                override fun onDataChange(p0: DataSnapshot) {

                        for (f in p0.children){
                            val film = f.getValue(Film::class.java)
                            Log.d("USUARIO ATUAL LOG","FILME ${film!!.title}")
                            filmList.add(film)

                        }

                }
            })
            if (filmList.contains(favorite)){
                callback(false,"Você já adicionou esse filme!")
            }else{
                userAtual.child("Favoritos").child("${favorite.id}").setValue(favorite)
                callback(true,"Adicionado aos favoritos com sucesso!")
            }
        }else{
            println("Usuário deslogado")
            callback(false,"Ops! Algo deu errado!")
        }
    }

    fun removeFilmUserFavorites(id: String, callback: (resultado: Boolean) -> Unit){
        if (mDatabaseReferenceUsers.child(mAuth.currentUser!!.uid).child("Favoritos").child(id).removeValue().isSuccessful){
            callback(false)
        }else{
            callback(true)
        }
    }


    fun retrieveFavoritesFromUser(callback: (films: Array<Film>)->Unit){
        var filmList: MutableList<Film> = mutableListOf()

        mDatabaseReferenceUsers.child(mAuth.currentUser!!.uid).child("Favoritos").addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                print("deu ruim")
            }
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    for (f in p0.children){
                        val film = f.getValue(Film::class.java)
                        Log.d("USUARIO ATUAL LOG","FILME ${film!!.title}")
                        filmList.add(film)

                    }
                    callback(filmList.toTypedArray())
                }else{
                    callback(filmList.toTypedArray())
                }
            }
        })
    }

    fun getCurrentUserData(callback: (user: Profile)->Unit){
        mDatabaseReferenceUsers.child(mAuth.currentUser!!.uid).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                print("deu ruim")
            }
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    val user = p0.getValue(Profile::class.java)
                    callback(user!!)
                }else{
                    print("vazio")
                }
            }
        })
    }

    fun retrieveUserDataWithLocation(location: LatLng, callback: (nome: String, films: Array<Film>) -> Unit) {
        val filmList: MutableList<Film> = mutableListOf()
        val latitude = location.latitude
        val longitude = location.longitude

        mDatabaseReferenceUsers.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                print("deu ruim")
            }
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    for (u in p0.children){
                        val user = u.getValue(Profile::class.java)
                        if (user!!.Latitude.equals(latitude.toString()) && user.Longitude.equals(longitude.toString())){
//                            val favorito = u.child("Favoritos").getValue(Film::class.java)
//                            filmList.add(favorito!!)
//                            Log.d("USER MAPA", "${user.Nome} ${filmList.toTypedArray().contentToString()}")
//                            callback(user.Nome, filmList.toTypedArray())
                            mDatabaseReferenceUsers.child("${u.key}").child("Favoritos").addValueEventListener(object : ValueEventListener{
                                override fun onCancelled(p0: DatabaseError) {
                                    print("deu ruim")
                                }
                                override fun onDataChange(p0: DataSnapshot) {
                                    if (p0.exists()){
                                        for (f in p0.children){
                                            val film = f.getValue(Film::class.java)
                                            filmList.add(film!!)
                                        }
                                        Log.d("USER MAPA", "${user.Nome} ${filmList.toTypedArray().contentToString()}")
                                        callback(user.Nome, filmList.toTypedArray())
                                    }else{
                                        callback(user.Nome, filmList.toTypedArray())
                                        print("vazio")
                                    }
                                }
                            })
                        }
                    }
                }else{
                    print("vazio")
                }
            }
        })
    }
}

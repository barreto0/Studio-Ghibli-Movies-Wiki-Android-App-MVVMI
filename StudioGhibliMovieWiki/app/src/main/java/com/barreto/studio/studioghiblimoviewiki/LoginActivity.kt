package com.barreto.studio.studioghiblimoviewiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener { login() }
        //btnLogin.setOnClickListener { signIn() }
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this). get(LoginViewModel::class.java)
    }

    private fun login(){
        val email = fieldEmail.text.toString()
        val senha = fieldSenha.text.toString()

        viewModel.login(email,senha)
        viewModel.resultLogin.observe(this, Observer {
            if(viewModel.resultLogin.value == true){
                Toast.makeText(this, viewModel.msg.value.toString(), Toast.LENGTH_SHORT).show()
                val intentMain = Intent(this, MainActivity::class.java)
                startActivity(intentMain)
            }else{
                Toast.makeText(this, viewModel.msg.value.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

//    val mAuth = FirebaseAuth.getInstance()
//    private fun signIn() {
//        val email = fieldEmail.text.toString()
//        val senha = fieldSenha.text.toString()
//
//        if (email.isEmpty()) {
//            Toast.makeText(this, "E-mail obrigatório!", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        if (senha.isEmpty()) {
//            Toast.makeText(this, "Senha obrigatória!", Toast.LENGTH_LONG).show()
//            return
//        } else {
//            if (senha.length < 6) {
//                Toast.makeText(
//                    this,
//                    "A senha precisa ter 6 caracteres no mínimo.",
//                    Toast.LENGTH_LONG
//                ).show()
//                return
//            }
//        }
//
//        val operation = mAuth.signInWithEmailAndPassword(email, senha)
//        operation.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val intentMain = Intent(this, MainActivity::class.java)
//                startActivity(intentMain)
//            } else {
//                val error = task.exception?.localizedMessage
//                    ?: "Não foi possível entrar no aplicativo no momento"
//                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
//            }
//        }
//    }

}

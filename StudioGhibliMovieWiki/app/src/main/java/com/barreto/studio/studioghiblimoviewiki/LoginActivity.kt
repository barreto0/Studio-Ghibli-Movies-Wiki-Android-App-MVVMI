package com.barreto.studio.studioghiblimoviewiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener { login() }
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this). get(LoginViewModel::class.java)
    }

    private fun login(){
        val email = fieldEmail.text.toString()
        val senha = fieldSenha.text.toString()
    }

}

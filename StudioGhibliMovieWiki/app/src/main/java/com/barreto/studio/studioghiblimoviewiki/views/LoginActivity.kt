package com.barreto.studio.studioghiblimoviewiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.barreto.studio.studioghiblimoviewiki.viewModel.LoginViewModel
import com.barreto.studio.studioghiblimoviewiki.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener { login() }
        btnSignUp.setOnClickListener { signUp() }
        btnForgotPassword.setOnClickListener{ forgotPassword() }
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this). get(LoginViewModel::class.java)
    }

    private fun login(){
        val email = emailFieldLogin.text.toString()
        val senha = senhaFieldLogin.text.toString()


        viewModel.login(email,senha)
        viewModel.resultLogin.observe(this, Observer {
            if(viewModel.resultLogin.value == true){

                val intentMain = Intent(this, MainActivity::class.java)
                startActivity(intentMain)
            }else{
                Toast.makeText(this, viewModel.msg.value.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun signUp(){
        val intentRegister = Intent(this, RegisterActivity::class.java)
        startActivity(intentRegister)

    }

    private fun forgotPassword(){
        val intentForgotPassword = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intentForgotPassword)
    }

}

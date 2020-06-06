package com.barreto.studio.studioghiblimoviewiki.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.barreto.studio.studioghiblimoviewiki.viewModel.UserViewModel
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.domain.BodyPostBot
import com.barreto.studio.studioghiblimoviewiki.repository.ChatBotRepository
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener { login() }
        btnSignUp.setOnClickListener { signUp() }
        btnForgotPassword.setOnClickListener{ forgotPassword() }
//        btnDebugMaps.setOnClickListener {
//            val intentMaps= Intent(this, MapsActivity::class.java)
//            startActivity(intentMaps)
//        }
        val body = BodyPostBot("oi","teste@teste.com","10")
        val cbotrepo = ChatBotRepository(this,"https://dialogflow-server-pdm.herokuapp.com/")
        cbotrepo.getResponseBot(body){
            Log.d("BOT CALLBACK", it.message)
        }

    }

    override fun onStop() {
        super.onStop()
        progressBarLogin.visibility = View.GONE
    }

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this). get(UserViewModel::class.java)
    }

    private fun login(){
        val email = emailFieldLogin.text.toString()
        val senha = senhaFieldLogin.text.toString()


        progressBarLogin.visibility = View.VISIBLE
        viewModel.login(email,senha)
        viewModel.result.observe(this, Observer {
            if(it == true){
                progressBarLogin.visibility = View.GONE
                val intentMain = Intent(this, MainActivity::class.java)
                startActivity(intentMain)
            }

        })
        viewModel.msg.observe(this, Observer {
            if(it != null){
                progressBarLogin.visibility = View.GONE
                Toast.makeText(this, viewModel.msg.value.toString(), Toast.LENGTH_SHORT).show()
            }
            viewModel.msg.value == null
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

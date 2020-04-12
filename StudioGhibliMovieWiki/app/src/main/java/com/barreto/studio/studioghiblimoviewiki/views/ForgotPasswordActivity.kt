package com.barreto.studio.studioghiblimoviewiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.viewModel.ForgotPasswordViewModel
import com.barreto.studio.studioghiblimoviewiki.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        btnSendPasswordReset.setOnClickListener { resetPassword() }
    }

    private val viewModel: ForgotPasswordViewModel by lazy {
        ViewModelProvider(this). get(ForgotPasswordViewModel::class.java)
    }

    private fun resetPassword(){
        val email = emailFieldForgotPassword.text.toString()
        val intentOperation = Intent(this,OperationResultActivity::class.java)

        viewModel.resetPassword(email)
        viewModel.result.observe(this, Observer {
            if(viewModel.result.value == false){
                Toast.makeText(this, "Insira o email corretamente", Toast.LENGTH_SHORT).show()
            }else{
                intentOperation.putExtra("text", "Email de recuperaçao enviado com sucesso")
                startActivity(intentOperation)
                Toast.makeText(this, "Deu bom", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

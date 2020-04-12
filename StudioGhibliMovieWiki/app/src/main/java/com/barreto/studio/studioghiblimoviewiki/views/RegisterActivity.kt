package com.barreto.studio.studioghiblimoviewiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.viewModel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        btnRegister.setOnClickListener { register() }

    }

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this). get(RegisterViewModel::class.java)
    }

    fun register(){
        val email = emailFieldRegister.text.toString()
        val senha = senhaFieldRegister.text.toString()
        val nome = nameFieldRegister.text.toString()
        val intentOperation = Intent(this,OperationResultActivity::class.java)

        viewModel.register(email,senha,nome)
        viewModel.resultRegister.observe(this, Observer {
            if(viewModel.resultRegister.value==false){

                Toast.makeText(this, viewModel.msg.value.toString(), Toast.LENGTH_SHORT).show()
            }else{
                //TODO: colocar intent para operation activity


                intentOperation.putExtra("text", "Usuário Criado Com Sucesso")
                startActivity(intentOperation)

                //Toast.makeText(this, viewModel.msg.value.toString(), Toast.LENGTH_SHORT).show()
            }
        })

    }
}

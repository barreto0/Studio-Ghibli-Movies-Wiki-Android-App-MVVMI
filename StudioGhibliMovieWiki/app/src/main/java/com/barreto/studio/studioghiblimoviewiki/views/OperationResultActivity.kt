package com.barreto.studio.studioghiblimoviewiki.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.barreto.studio.studioghiblimoviewiki.R
import kotlinx.android.synthetic.main.activity_operation_result.*

class OperationResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation_result)

        val text: String? = intent.getStringExtra("text")
        tvResultOperation.text = text

        btnAckOperation.setOnClickListener { ack() }

    }

    private fun ack(){
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)

    }
}

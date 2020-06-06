package com.barreto.studio.studioghiblimoviewiki.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.domain.BodyPostBot
import com.barreto.studio.studioghiblimoviewiki.domain.Message
import com.barreto.studio.studioghiblimoviewiki.viewModel.ChatBotViewModel
import com.barreto.studio.studioghiblimoviewiki.viewModel.UserViewModel
import com.barreto.studio.studioghiblimoviewiki.views.adapter.ChatBotAdapter
import kotlinx.android.synthetic.main.activity_chat_bot.*

class ChatBotActivity : AppCompatActivity() {

    var mensagens = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)
        title = "ChatBot"

        btnSendMessage.setOnClickListener{ SendMessage() }
        configureRecyclerView()
        showMessages()
    }

    private val viewModelBot: ChatBotViewModel by lazy {
        ViewModelProvider(this).get(ChatBotViewModel::class.java)
    }

    private val viewModelUser: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    private fun configureRecyclerView(){
        rvChatBot.layoutManager = LinearLayoutManager(this) //falando para a atividade mostrar os dados de forma linear vertical
    }

    private fun SendMessage(){
        viewModelUser.getCurrentUserData()
        val text = sendTextChatBot.text.toString()
        var email: String = ""
        var userMessage = Message("user",text)
        mensagens.add(userMessage)
        viewModelUser.email.observe(this, Observer {
            email = it
        })
        val sessionId = "123456789"
        val body = BodyPostBot(text, email, sessionId)
        viewModelBot.getResponseBot(body)
    }

    private fun showMessages(){
        val mes = mutableListOf<Message>()
        viewModelBot.result.observe(this, Observer {
            mes.add(it)
            val adapter = ChatBotAdapter(mes.toTypedArray())
            rvChatBot.adapter = adapter
        })



    }
}

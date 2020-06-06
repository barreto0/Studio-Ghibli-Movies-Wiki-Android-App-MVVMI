package com.barreto.studio.studioghiblimoviewiki.interactor

import android.content.Context
import com.barreto.studio.studioghiblimoviewiki.domain.BodyPostBot
import com.barreto.studio.studioghiblimoviewiki.domain.Message
import com.barreto.studio.studioghiblimoviewiki.repository.ChatBotRepository

class ChatBotInteractor(private val context: Context) {

    private val chatRepository = ChatBotRepository(context,"https://dialogflow-server-pdm.herokuapp.com/")

    fun getResponseBot(body: BodyPostBot,callback: (message: Message)->Unit){
        chatRepository.getResponseBot(body, callback)
    }
}
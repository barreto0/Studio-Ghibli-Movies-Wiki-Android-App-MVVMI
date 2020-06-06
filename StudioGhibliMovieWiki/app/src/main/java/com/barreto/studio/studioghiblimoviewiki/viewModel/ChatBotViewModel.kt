package com.barreto.studio.studioghiblimoviewiki.viewModel

import android.app.Application
import android.os.Message
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.barreto.studio.studioghiblimoviewiki.domain.BodyPostBot
import com.barreto.studio.studioghiblimoviewiki.interactor.ChatBotInteractor

class ChatBotViewModel(val app: Application) : AndroidViewModel(app) {
    private val interactor = ChatBotInteractor(app.applicationContext)

    val result = MutableLiveData<com.barreto.studio.studioghiblimoviewiki.domain.Message>()
    lateinit var mes: List<com.barreto.studio.studioghiblimoviewiki.domain.Message>

    fun getResponseBot(body: BodyPostBot){
        interactor.getResponseBot(body){
            result.value = it
        }

    }
}
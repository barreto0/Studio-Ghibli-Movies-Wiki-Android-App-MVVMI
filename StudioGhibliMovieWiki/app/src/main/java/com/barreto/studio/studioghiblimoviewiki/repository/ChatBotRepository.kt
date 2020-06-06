package com.barreto.studio.studioghiblimoviewiki.repository

import android.content.Context
import android.util.Log
import com.barreto.studio.studioghiblimoviewiki.domain.BodyPostBot
import com.barreto.studio.studioghiblimoviewiki.domain.Message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatBotService{

    @POST("api/message/text/send")
    fun getResponseBot(
        @Body body: BodyPostBot
    ): Call<List<MessageDTO>>

}

class ChatBotRepository(context: Context, baseUrl: String) : BaseRetrofit(context, baseUrl) {

    private val service = retrofit.create(ChatBotService::class.java)

    fun getResponseBot(body: BodyPostBot,callback: (message: Message)->Unit){

        service.getResponseBot(body).enqueue(object : Callback<List<MessageDTO>> {

            override fun onResponse(call: Call<List<MessageDTO>>, response: Response<List<MessageDTO>>) {

                val result = response.body()
                val domain = Message("bot",result?.get(0)?.queryResult?.fulfillmentText)

                Log.d("BOT",result.toString())
                Log.d("BOT", domain.message)

                callback(domain)

            }
            override fun onFailure(call: Call<List<MessageDTO>>, t: Throwable) {
                Log.d("BOT FAIÔ", t.toString())
                callback(Message("FAIÔ","FAIÔ")) //array vazio p n quebrar o app
            }
        })
    }
}
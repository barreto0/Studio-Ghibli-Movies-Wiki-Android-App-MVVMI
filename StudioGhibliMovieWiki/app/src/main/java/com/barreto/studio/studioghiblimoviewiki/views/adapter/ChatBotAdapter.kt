package com.barreto.studio.studioghiblimoviewiki.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.domain.Message
import kotlinx.android.synthetic.main.chatbot_message.view.*

class ChatBotAdapter (private val data: Array<Message>): RecyclerView.Adapter<ChatBotAdapter.ChatBotViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatBotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chatbot_message,parent,false)
        return ChatBotViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatBotViewHolder, position: Int) {
        val message = data[position]
        holder.message.text = message.message
    }

    class ChatBotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message: TextView = itemView.tvMessageUser

    }

}

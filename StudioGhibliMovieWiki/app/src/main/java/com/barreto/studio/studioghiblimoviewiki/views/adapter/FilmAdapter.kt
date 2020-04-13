package com.barreto.studio.studioghiblimoviewiki.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import kotlinx.android.synthetic.main.film_list_item.view.*

class FilmAdapter (private val data: Array<Film>) :
    RecyclerView.Adapter<FilmAdapter.FilmViewHolder>(){

    //Recycler -> ViewHolder -> Adapter

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        //aqui pega o formato visual que será apresentado nas telas
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_list_item,parent,false)
        return FilmViewHolder(view)
    }


    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        //pega os dados do array de filmes e passa para a holder q passa para a recycle
        val film = data[position]
        holder.title.text = film.title
        holder.description.text = film.description
        holder.releaseDate.text = "Ano de estreia: " + film.releaseDate
        holder.director.text = "Diretor: " + film.director
    }

    class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //classe que aponta para os objetos visuais
        val title: TextView = itemView.tvTitle
        val description: TextView = itemView.tvDescription
        val releaseDate: TextView = itemView.tvReleaseDate
        val director: TextView = itemView.tvDirector

    }
}
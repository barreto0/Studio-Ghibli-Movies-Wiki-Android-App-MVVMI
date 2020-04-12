package com.barreto.studio.studioghiblimoviewiki.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.barreto.studio.studioghiblimoviewiki.R
import com.barreto.studio.studioghiblimoviewiki.domain.Film
import com.barreto.studio.studioghiblimoviewiki.repository.FilmDTO
import kotlinx.android.synthetic.main.list_movie.view.*


class FilmAdapter(private val dataSet: Array<Film>) :
    RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        var view: View? = null

            view = LayoutInflater.from(parent.context).inflate(R.layout.list_movie, parent, false)

        return FilmViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = dataSet[position]
        holder.title.text = film.title
        holder.description.text=film.description
        holder.director.text=film.director
        holder.releasedate.text=film.releaseDate

    }


    class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.tvtitle
        val description: TextView = itemView.tvdescription
        val director : TextView = itemView.tvdirector
        val releasedate : TextView = itemView.tvreleasedate
    }

}

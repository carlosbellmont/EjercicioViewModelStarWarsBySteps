package com.cbellmont.ejercicioadapterstarwars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FilmsAdapter : RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder>() {


    class FilmsViewHolder(root: View, var tvNombre: TextView, var tvIntro: TextView, var ivDerecha: ImageView, var ivIzquierda: ImageView) : RecyclerView.ViewHolder(root)

    private var films = mutableListOf<Film>()

    fun addFilmToList(film: Film) {
        this.films.add(film)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_layout, parent, false)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvIntro = view.findViewById<TextView>(R.id.tvIntro)
        val ivDerecha = view.findViewById<ImageView>(R.id.ivDerecha)
        val ivIzquierda = view.findViewById<ImageView>(R.id.ivIzquierda)

        return FilmsViewHolder(view, tvName, tvIntro, ivDerecha, ivIzquierda)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.tvIntro.text = films[position].intro
        holder.tvNombre.text = films[position].name
        if (position % 6 > 2){
            holder.ivIzquierda.visibility = View.GONE
            holder.ivDerecha.visibility = View.VISIBLE
            Picasso.get().load(films[position].getUrlImage()).into(holder.ivDerecha)
        }  else {
            holder.ivIzquierda.visibility = View.VISIBLE
            holder.ivDerecha.visibility = View.GONE
            Picasso.get().load(films[position].getUrlImage()).into(holder.ivIzquierda)
        }
    }
}

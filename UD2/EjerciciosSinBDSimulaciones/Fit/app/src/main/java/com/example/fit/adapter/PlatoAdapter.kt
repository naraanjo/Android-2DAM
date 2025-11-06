package com.example.fit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fit.R
import com.example.fit.model.PlatoModel

class PlatoAdapter(private val platos: List<PlatoModel>):
RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder>(){

    class PlatoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nombre: TextView = itemView.findViewById(R.id.tvNombrePlato)
        val infoAdicional: TextView = itemView.findViewById(R.id.tvInfoAdicional)
        val foto: ImageView = itemView.findViewById(R.id.imgPlato)
        val tipoComida: TextView = itemView.findViewById(R.id.tvTipoComida)

    }

    // XML item_plato
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatoViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plato, parent, false)
        return PlatoViewHolder(view)
    }

    // Lleno el contenido del recycler
    // Holder guarda la referencia a cada item -puntero-
    override fun onBindViewHolder(holder: PlatoViewHolder, position: Int) {


        // Item a item
        // Holder relacionado con el item_plato.xml
        val plato = platos[position]
        holder.nombre.text = plato.nombre
        holder.infoAdicional.text = plato.infoAdicional
        holder.foto.setImageResource(plato.foto)
        holder.tipoComida.text = plato.tipoComida

    }
    override fun getItemCount(): Int = platos.size

}
package com.example.fit.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fit.MainActivity3
import com.example.fit.R
import com.example.fit.model.DiaModel

class DiaAdapter(private val dias: List<DiaModel>) :
    RecyclerView.Adapter<DiaAdapter.DiaViewHolder>() {

    class DiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.nombre) // Del item_dia
        val kcal: TextView = itemView.findViewById(R.id.kcal) // Del item_dia
    }

    // Vista item_dia.xml, la infla para luego llenarla en onBindViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dia, parent, false)
        return DiaViewHolder(view)
    }

    //Llena de contenido el recycler
    // Holder guarda la referencia a cada item -puntero-
    override fun onBindViewHolder(holder: DiaViewHolder, position: Int) {
        // Item a item
        val dia = dias[position]
        holder.nombre.text = dia.nombre
        holder.kcal.text = "${dia.kcal} kcal"

        // Cada item le doy la funcion de que sea un boton
        holder.itemView.setOnClickListener {


            // Intent para abrir la nuevaActivty
            val context = holder.itemView.context // Para saber desde donde se inicia
            val intent = Intent(context, MainActivity3::class.java)

            // Paso el dia
            intent.putExtra("nombreDia", dia.nombre)

            // Inicio la activty
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = dias.size
}

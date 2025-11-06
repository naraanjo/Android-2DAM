package com.example.practicaexamen1.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaexamen1.R
import com.example.practicaexamen1.model.ActividadModel


class ActividadAdapter(private val actividades: MutableList<ActividadModel>):
    RecyclerView.Adapter<ActividadAdapter.ActividadViewHolder>(){


    class ActividadViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        val nombreActividad: TextView = itemView.findViewById(R.id.tvNombreActividad)
        val dificultadActividad: TextView = itemView.findViewById(R.id.tvDificultadActividad)


    }


    // XML item_Actividad
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActividadViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_actividad, parent, false)
        return ActividadViewHolder(view)
    }


    // Lleno el contenido del recycler
    // Holder guarda la referencia a cada item -puntero-
    override fun onBindViewHolder(holder: ActividadViewHolder, position: Int) {




        // Item a item
        // Holder relacionado con el item_actividad.xml
        val actividad = actividades[position]
        holder.nombreActividad.text = actividad.nombre
        holder.dificultadActividad.text = actividad.dificultad





        // Al dejarlo pulsado se elimina
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Eliminar actividad")
                .setMessage("¿Estás seguro de que quieres eliminar esta actividad?")
                .setPositiveButton("Aceptar") {accion , _ ->
                    actividades.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemCount)
                    accion.dismiss() // Oculta el dialogo
                }
                .setNegativeButton("Cancelar"){dialogo, _ ->
                    dialogo.dismiss()
                }
                .show()


            true // Se debe de poner con onLongClick - indica evento consumido
        }


        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Has pulsado en ${actividad.nombre}", Toast.LENGTH_SHORT).show()
        }
    }
    override fun getItemCount(): Int = actividades.size


}

package com.example.practicaexamen1.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaexamen1.R
import com.example.practicaexamen1.model.TareaModel



// Conecta los datos con la vista
class TareaAdapter(private val tareas: MutableList<TareaModel>):
    RecyclerView.Adapter<TareaAdapter.TareaViewHolder>(){


    class TareaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        val nombre: TextView = itemView.findViewById(R.id.tvNombreTarea)
        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcionTarea)



    }


    // XML item_tarea
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)
        return TareaViewHolder(view)
    }


    // Lleno el contenido del recycler
    // Holder guarda la referencia a cada item -puntero-
    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {




        // Item a item
        // Holder relacionado con el item_tarea.xml
        val tarea = tareas[position]
        holder.nombre.text = tarea.nombre
        holder.descripcion.text = tarea.descripcion





        // Al dejarlo pulsado se elimina
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Eliminar tarea")
                .setMessage("¿Estás seguro de que quieres eliminar este tarea?")
                .setPositiveButton("Aceptar") {accion , _ ->
                    tareas.removeAt(position)
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
    }
    override fun getItemCount(): Int = tareas.size


}

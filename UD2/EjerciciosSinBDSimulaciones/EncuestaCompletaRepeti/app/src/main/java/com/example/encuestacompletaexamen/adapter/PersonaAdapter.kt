package com.example.encuestacompletaexamen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.encuestacompletaexamen.Model.Persona
import com.example.encuestacompletaexamen.R


class PersonaAdapter(private var personas: MutableList<Persona>):
    RecyclerView.Adapter<PersonaAdapter.PersonaViewHolder>(){

    class PersonaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nombre: TextView = itemView.findViewById(R.id.tvNombre)
        val empresa: TextView = itemView.findViewById(R.id.tvEmpresa)

    }




    // XML item_plato
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_persona, parent, false)
        return PersonaViewHolder(view)
    }




    // Lleno el contenido del recycler
    // Holder guarda la referencia a cada item -puntero-
    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int) {


        // Item a item
        // Holder relacionado con el item_persona.xml
        val persona = personas[position]
        holder.nombre.text = persona.nombre
        holder.empresa.text = persona.empresa



        // Al dejarlo pulsado se elimina
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Eliminar persona")
                .setMessage("¿Estás seguro de que quieres eliminar esta persona?")
                .setPositiveButton("Aceptar") {accion , _ ->
                    personas.removeAt(position)
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
    override fun getItemCount(): Int = personas.size


    fun actualizarLista(nuevaLista: List<Persona>) {
        personas = nuevaLista as MutableList<Persona>
        notifyDataSetChanged() // Este método le dice al adapter que los datos han cambiado
    }
}



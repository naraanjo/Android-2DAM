
package com.example.gestorrecetetas.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gestorrecetetas.database.Receta
import com.example.gestorrecetetas.databinding.ItemRecetaBinding


class RecetaAdapter(private var recetas: List<Receta>) : RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder>() {

    /**
     * ViewHolder: Representa una única fila (un 'item') en el RecyclerView.
     * Contiene las referencias a las vistas dentro del layout 'item_serie.xml'.
     * Usamos View Binding aquí también para acceder a las vistas de forma segura.
     */
    inner class RecetaViewHolder(val binding: ItemRecetaBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Se llama cuando el RecyclerView necesita crear un nuevo ViewHolder (una nueva fila).
     * Infla el layout 'item_serie.xml' y crea una instancia de SerieViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        // Inflamos el layout para cada item usando View Binding
        val binding = ItemRecetaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecetaViewHolder(binding)
    }

    /**
     * Se llama para mostrar los datos en una posición específica.
     * Vincula los datos del objeto 'Serie' en la posición 'position' con las vistas del ViewHolder.
     */
    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {

        val receta = recetas[position] // Obtenemos la serie actual de la lista
        // Usamos el 'holder.binding' para acceder a los TextViews y asignarles los datos
        holder.binding.tvNombre.text = receta.nombre
        holder.binding.tvPrecio.text = "${receta.precio} €"
        holder.binding.tvDificultad.text = "Dificultad: ${receta.dificultad}"

    }

    /**
     * Devuelve el número total de items en la lista de datos.
     */
    override fun getItemCount(): Int {
        return recetas.size
    }

    /**
     * Función personalizada para actualizar la lista de series en el adaptador.
     * Después de actualizar los datos, notifica al RecyclerView para que se redibuje.
     */
    fun actualizarLista(nuevaLista: List<Receta>) {
        recetas = nuevaLista
        notifyDataSetChanged() // Este método le dice al adapter que los datos han cambiado
    }
}

package com.example.gimnasio.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio.database.Cliente
import com.example.gimnasio.databinding.ItemClienteBinding


class ClienteAdapter(private var clientes: List<Cliente>) :
    RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    /**
     * ViewHolder: Representa una única fila (un 'item') en el RecyclerView.
     * Contiene las referencias a las vistas dentro del layout 'item_serie.xml'.
     * Usamos View Binding aquí también para acceder a las vistas de forma segura.
     */
    inner class ClienteViewHolder(val binding: ItemClienteBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * Se llama cuando el RecyclerView necesita crear un nuevo ViewHolder (una nueva fila).
     * Infla el layout 'item_serie.xml' y crea una instancia de SerieViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        // Inflamos el layout para cada item usando View Binding
        val binding = ItemClienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClienteViewHolder(binding)
    }

    /**
     * Se llama para mostrar los datos en una posición específica.
     * Vincula los datos del objeto 'Serie' en la posición 'position' con las vistas del ViewHolder.
     */
    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clientes[position] // Obtenemos la serie actual de la lista

        // Usamos el 'holder.binding' para acceder a los TextViews y asignarles los datos
        holder.binding.tvNombre.text = cliente.nombre
        holder.binding.tvEmpleo.text = cliente.empleo
        holder.binding.tvEdad.text = cliente.edad.toString()

    }

    /**
     * Devuelve el número total de items en la lista de datos.
     */
    override fun getItemCount(): Int {
        return clientes.size
    }

    /**
     * Función personalizada para actualizar la lista de series en el adaptador.
     * Después de actualizar los datos, notifica al RecyclerView para que se redibuje.
     */
    fun actualizarLista(nuevaLista: List<Cliente>) {
        clientes = nuevaLista
        notifyDataSetChanged() // Este método le dice al adapter que los datos han cambiado
    }
}

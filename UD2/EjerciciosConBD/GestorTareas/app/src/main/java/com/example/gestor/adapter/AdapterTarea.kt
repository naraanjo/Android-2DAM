import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gestor.MainActivity3
import com.example.gestor.R
import com.example.gestor.database.Tarea
import com.example.gestor.databinding.DialogInfoTareaBinding


class AdapterTarea(private var tareas: MutableList<Tarea>, private val onDeleteClick: (Tarea) -> Unit):
    RecyclerView.Adapter<AdapterTarea.TareaViewHolder>(){

    class TareaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val fecha: TextView = itemView.findViewById(R.id.tvFecha)
        val titulo: TextView = itemView.findViewById(R.id.tvTitulo)

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
        holder.descripcion.text = tarea.descripcion
        holder.fecha.text = tarea.fecha
        holder.titulo.text = tarea.titulo

        // Al dejarlo pulsado se elimina
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Eliminar tarea")
                .setMessage("¿Estás seguro de que quieres eliminar esta tarea?")
                .setPositiveButton("Aceptar") {accion , _ ->
                    tareas.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemCount)
                    onDeleteClick(tarea) // Callback para eliminar la tarea
                    accion.dismiss() // Oculta el dialogo
                }
                .setNegativeButton("Cancelar"){dialogo, _ ->
                    dialogo.dismiss()
                }
                .show()
            true // Se debe de poner con onLongClick - indica evento consumido
        }

        // Pulsacion corta
        holder.itemView.setOnClickListener {

            val context = holder.itemView.context
            // Abrir una nueva venta para el ejercicio de clase
            val intent = Intent(context, MainActivity3::class.java)

            // Envio la tarea
            intent.putExtra("tarea_key",tarea)
            context.startActivity(intent)





//            // Inflar el layout del diálogo
//            val dialogBinding = DialogInfoTareaBinding.inflate(LayoutInflater.from(holder.itemView.context))
//
//            // Asignar valores de la tarea
//            dialogBinding.tvTitulo.text = tarea.titulo
//            dialogBinding.tvDescripcion.text = tarea.descripcion
//            dialogBinding.tvFecha.text = "Fecha: ${tarea.fecha}"
//            dialogBinding.tvHora.text = "Hora: ${tarea.hora}"
//
//            // Crear el AlertDialog
//            val dialog = AlertDialog.Builder(holder.itemView.context)
//                .setView(dialogBinding.root)
//                .setPositiveButton("Cerrar") { d, _ -> d.dismiss() }
//                .create()
//
//            dialog.show()

        }
    }
    override fun getItemCount(): Int = tareas.size

    fun actualizarLista(nuevaLista: List<Tarea>) {
        tareas = nuevaLista as MutableList<Tarea>
        notifyDataSetChanged() // Este método le dice al adapter que los datos han cambiado
    }
}

package adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewejemplobasico.R

// Mantiene la referencia con la vista
class CantanteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.txCantante)
    var isBackgroundColorChanged = false
}
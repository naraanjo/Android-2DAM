package com.example.gestor

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gestor.database.Tarea
// Importa la clase de ViewBinding generada
import com.example.gestor.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root) // Usar binding.root

        actualizarUI()
        confBtn()
    }

    private fun actualizarUI() {

        // Recoger la tarea
        val tarea = recogerObjetoTarea()

        //  UI con los datos de la tarea
        if (tarea != null) {
            binding.textTituloTarea.text = tarea.titulo
            binding.textDescripcionTarea.text = tarea.descripcion
            binding.textFechaTarea.text = "Fecha: ${tarea.fecha}"
            binding.textHora.text = "Hora: ${tarea.hora}"
        } else {
            // Manejar en caso de que la tarea llegue nula
            binding.textTituloTarea.text = "Error"
            binding.textDescripcionTarea.text = "No se pudo cargar la información de la tarea."
        }

        // Configurar el botón de volver
        binding.btnVolver.setOnClickListener {
            finish() // Cierra esta Activity y regresa a la anterior
        }
    }

    private fun recogerObjetoTarea(): Tarea? {
        val key = "tarea_key"

        return if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra(key, Tarea::class.java)
        }else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(key)
        }
    }

    private fun confBtn(){
        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
}
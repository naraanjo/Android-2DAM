package com.example.practicaexamen1


import android.os.Build
import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaexamen1.*
import com.example.practicaexamen1.adapter.TareaAdapter
import com.example.practicaexamen1.databinding.ActivityMain2Binding
import com.example.practicaexamen1.model.PersonaModel
import com.example.practicaexamen1.provider.TareaProvider


class MainActivity2 : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializo el binding
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Iniciar el recycler
        // Datos de provider de todas las tareas
        configurarRecycler()



        // Capturo el objeto enviado
         val personaModel = capturarObjeto()
        binding.txvSaludo.text = personaModel?.nombre


        // Vuelve a la activity anterior
        binding.btnVolver.setOnClickListener {
            finish()
        }


    }


    private fun configurarRecycler(){

        // Datos de provider de todas las tareas

        val listaTareas = TareaProvider.obtenerTareas()

        binding.rvTarea.layoutManager = LinearLayoutManager(this)
        binding.rvTarea.adapter = TareaAdapter(listaTareas.toMutableList())


    }


    // Funcion para obtener el objeto de la otra activity
    private fun capturarObjeto(): PersonaModel? {

        val key="PERSONA_KEY"
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, PersonaModel::class.java)
        } else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(key)
        }
    }
}
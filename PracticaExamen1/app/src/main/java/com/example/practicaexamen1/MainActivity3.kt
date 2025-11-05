package com.example.practicaexamen1

import ActividadProvider
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaexamen1.adapter.ActividadAdapter
import com.example.practicaexamen1.databinding.ActivityMain3Binding
import com.example.practicaexamen1.model.PersonaModel

class MainActivity3 : AppCompatActivity() {

    //Declaro el binding
    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializo el binding
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarRecycler()


        val personaCliente = recogerObjetoPersona()
        binding.txvSaludoCliente.text = "Hola ${personaCliente?.nombre}" // Actualizo la UI


        // Inicio
        binding.btnInicio.setOnClickListener {
            finish()
        }
    }


    // Obtengo el objeto enviado desde la otra activity
    private fun recogerObjetoPersona(): PersonaModel? {
        val key = "PERSONA_KEY"

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, PersonaModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(key)
        }
    }

    private fun configurarRecycler() {

        // Datos de provider de todas las actividades

        val listaActividades = ActividadProvider.obtenerActividades()
        binding.rvActividades.layoutManager = LinearLayoutManager(this)
        binding.rvActividades.adapter = ActividadAdapter(listaActividades)
    }
}


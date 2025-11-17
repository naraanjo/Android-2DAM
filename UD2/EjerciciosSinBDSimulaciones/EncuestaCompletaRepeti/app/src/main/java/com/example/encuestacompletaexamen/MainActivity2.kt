package com.example.encuestacompletaexamen

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.encuestacompletaexamen.Model.Persona
import com.example.encuestacompletaexamen.adapter.PersonaAdapter
import com.example.encuestacompletaexamen.databinding.ActivityMain2Binding
import com.example.encuestacompletaexamen.provider.PersonaProvider

class MainActivity2 : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializo el binding
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val personaRecibida =capturarPersona()
        PersonaProvider.getPersonas().add(personaRecibida!!) // Añado la persona al provider
        configurarRecycler()
    }

    // Funcion para capturar a la persona
    private fun capturarPersona(): Persona?{
        val key = "persona_enviada" // CAMBIAR

        return if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra(key, Persona::class.java)
        }else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(key)
        }
    }

    private fun configurarRecycler(){


        // Datos de provider de todas las personas
        val listaActividades = PersonaProvider.getPersonas()
        binding.rvPersonas.layoutManager = LinearLayoutManager(this)
        binding.rvPersonas.adapter = PersonaAdapter(listaActividades)
    }




}
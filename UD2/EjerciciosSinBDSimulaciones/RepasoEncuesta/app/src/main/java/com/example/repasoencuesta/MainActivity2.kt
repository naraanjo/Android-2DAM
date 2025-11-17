package com.example.repasoencuesta

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.repasoencuesta.adapter.PersonaAdapter
import com.example.repasoencuesta.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Incializo el binding
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarRecycler()

        val nuevaPersona = recogerObjetoPersona()
        PersonaProvider.getPersons().add(nuevaPersona!!)

        cofgBotones()
    }

    // Funcion para recoger el objeto
    private fun recogerObjetoPersona(): Persona? {
        val key = "1234" // CAMBIAR

        return if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra(key, Persona::class.java)
        }else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(key)
        }
    }

    // Configuro el recycler
    private fun configurarRecycler(){


        // Datos de provider de todas las personas
        val personas = PersonaProvider.getPersons()
        binding.rvPersonas.layoutManager = LinearLayoutManager(this)
        binding.rvPersonas.adapter = PersonaAdapter(personas)
    }

    // Configuro los click
    private fun cofgBotones(){

        binding.btnVolver.setOnClickListener {
            finish()
        }

        binding.btnContarr.setOnClickListener {
            Toast.makeText(this, "Total de personas registradas: ${PersonaProvider.getPersons().size}", Toast.LENGTH_SHORT).show()

        }

        binding.btnReiniciar.setOnClickListener{
            Toast.makeText(this, "Eliminando...", Toast.LENGTH_SHORT).show()
            binding.rvPersonas.adapter = PersonaAdapter(emptyList<Persona>() as MutableList<Persona>) // Limpio la recycler

        }

    }

}
package com.example.preexamencompletologin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.preexamencompletologin.adapter.PersonaAdapter
import com.example.preexamencompletologin.databinding.ActivityMain3Binding
import com.example.preexamencompletologin.databinding.ActivityMainBinding
import com.example.preexamencompletologin.provider.PersonaProvider

class MainActivity3 : AppCompatActivity() {
    // Declaro el binding
    private lateinit var binding: ActivityMain3Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializo el binding
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarRecycler()
    }

    private fun configurarRecycler(){
        // Datos de provider de todas las personas
        val listaPersonas = PersonaProvider.getPersons()
        binding.rvPersonas.layoutManager = LinearLayoutManager(this)
        binding.rvPersonas.adapter = PersonaAdapter(listaPersonas)
    }



}
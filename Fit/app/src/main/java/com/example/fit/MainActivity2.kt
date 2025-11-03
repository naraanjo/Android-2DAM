package com.example.fit

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fit.adapter.DiaAdapter
import com.example.fit.databinding.ActivityMain2Binding
import com.example.fit.databinding.ActivityMainBinding
import com.example.fit.provider.DiaProvider

class MainActivity2 : AppCompatActivity() {

    // Creo el binding
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializo el binding
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Con el adapter rellena uno a uno cada item actualizando el layout
        val recycler = binding.recyclerViewDias
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = DiaAdapter(DiaProvider.getDias())

        // Boton atras
        binding.btnAtras.setOnClickListener {
            finish()
        }

    }
}
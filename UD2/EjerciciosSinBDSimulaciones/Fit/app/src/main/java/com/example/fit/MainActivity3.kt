package com.example.fit

import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fit.adapter.DiaAdapter
import com.example.fit.adapter.PlatoAdapter
import com.example.fit.databinding.ActivityMain3Binding
import com.example.fit.provider.PlatoProvider

class MainActivity3 : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        // Inicializo el binding
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Boton de volver
        binding.btnVolver.setOnClickListener {
            finish() // Cierro la activity actual
        }

        val diaSeleccionado = reciboDia()

        when(diaSeleccionado){
            "Lunes" -> seleccion("Lunes")
            "Martes" -> seleccion("Martes")
            "Miercoles" ->seleccion("Miercoles")
            "Jueves" -> seleccion("Jueves")
            "Viernes" -> seleccion("Viernes")
            "Sabado" -> seleccion("Sabado")
            "Domingo" ->seleccion("Domingo")

        }
    }

    private fun seleccion(diaSeleccionado: String){
        binding.txvDia.text = diaSeleccionado

        // Adapter rellena 1 a 1 cada item del recycler
        val recycler = binding.recyclerViewPlatos
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = PlatoAdapter(PlatoProvider.getPlatosPorDia(diaSeleccionado))

    }
    // Funcion para recibir la info de la otra activity
    private fun reciboDia(): String? {

        
        val key="nombreDia"
        
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getStringExtra(key)
        } else {
            intent.getStringExtra(key)
        }

    }
}
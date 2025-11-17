package com.example.preexamencompletologin

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.preexamencompletologin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Iicializo el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        configBotones()
    }

    // Validar form
    private fun validarForm(): Boolean {

        // Compruebo que los datos de la UI, estan completos
        if (binding.edNombre.text.isEmpty() || binding.edFecha.text.isEmpty() || binding.edTelefono.text.isEmpty()
            || binding.edEmail.text.isEmpty()
        ) {

            Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
            return false
        } else {
            Toast.makeText(this, "Continuando...", Toast.LENGTH_SHORT).show()
            return true
        }
    }

    // Eventos click
    private fun configBotones() {

        // Abre una ventana nuevo pasando los datos
        binding.btnContinuar.setOnClickListener {

            val valido = validarForm()

            if (valido) {
                // Obtengo todos los datos de la UI
                val nombre = binding.edNombre.text.toString()
                val fecha = binding.edFecha.text.toString()
                val email = binding.edEmail.text.toString()
                val numero = binding.edTelefono.text.toString()

                // Abrir una nueva ventana
                val intent = Intent(this, MainActivity2::class.java)
                // Pasamos los datos
                intent.putExtra("nombre", nombre)
                intent.putExtra("numero", numero)
                intent.putExtra("email", email)
                intent.putExtra("fecha", fecha)
                startActivity(intent)
            }
        }
    }
}
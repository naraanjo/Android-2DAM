package com.example.gimnasio

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.gimnasio.databinding.ActivityMain2Binding
import com.example.gimnasio.databinding.ActivityMain3Binding
import com.example.gimnasio.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializo el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarBotones()
    }

    private fun configurarBotones() {

        // Dar de alta a un cliente
        binding.btnAAdir.setOnClickListener {

            configAñadir() // Abre la ventana de registro

        }

        // Entrar
        binding.btnEntrar.setOnClickListener {

            confEmpleado() // Abre la ventana del empleado
        }

    }

    private fun configAñadir() {

        // Abrimos la ventana de registrar a un usuario
        val intent = Intent(this, MainActivity3::class.java)
        startActivity(intent)
    }

    private fun confEmpleado(){
        // Abrimos la ventana del empleado
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }



}

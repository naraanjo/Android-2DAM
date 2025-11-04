package com.example.practicaexamen1

import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicaexamen1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicio el biding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarSeekBar()

        // btnInicio
        binding.btnIniciar.setOnClickListener {

            // Abro la siguiente activity
            // TODO
        }

    }

    // Configuracion de la seekBar
    private fun configurarSeekBar() {
        // Valor máximo y actual
        binding.skBarEdad.max = 100
        binding.skBarEdad.progress = 0

        // Mostrar valor inicial
        binding.txvEdad.text = "Edad: ${binding.skBarEdad.progress}"

        // Escuchar cambios en la SeekBar
        binding.skBarEdad.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Actualiza el TextView con el valor actual
                binding.txvEdad.text = "Edad: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // (Opcional) algo cuando el usuario empieza a mover
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // (Opcional) algo cuando el usuario suelta el dedo
            }
        })
    }
}
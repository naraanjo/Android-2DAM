package com.example.encuesta

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.encuesta.databinding.ActivityResumenBinding

class ResumenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResumenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar el binding
        binding = ActivityResumenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Capturo el objeto Encuesta
        val encuesta: Encuesta? = capturarEncuesta()

        // 2. Mostrar los datos de la encuesta
        if (encuesta != null) {
            // Mostramos los datos de la encuesta recibida usando su método toString()
            binding.tvResumenDatos.text = encuesta.toString()
        } else {
            binding.tvResumenDatos.text = "Error: No se pudo cargar la encuesta. Vuelve a intentarlo."
            Toast.makeText(this, "Error: Datos de encuesta no encontrados", Toast.LENGTH_LONG).show()
        }

        // 3. Configurar el botón VOLVER
        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun capturarEncuesta(): Encuesta? {
        val key = "EXTRA_ENCUESTA"

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Método moderno (API 33+) para un solo objeto Parcelable
            intent.getParcelableExtra(key, Encuesta::class.java)
        } else {
            // Método antiguo (compatible con APIs bajas)
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(key)
        }
    }
}

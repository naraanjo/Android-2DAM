package com.example.encuesta

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.encuesta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // BINDING
    private lateinit var binding: ActivityMainBinding

    private var listaEncuestas: ArrayList<Encuesta> = ArrayList() // Lista para el historial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializo la view y establezco el contenido
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configSeekBar() // Configuracion inicial de la SeekBar
        configurarListeners() // Configuracion inicial de todos los botones
    }

    // Configuracion de la seekBar
    private fun configSeekBar() {
        // Le doy el valor de la seekBar
        binding.tvHorasEstudio.text = binding.seekBarEstudio.progress.toString()

        // Actualización del valor en tiempo real
        binding.seekBarEstudio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvHorasEstudio.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    // Configuracion de los listeners
    private fun configurarListeners() {

        // Switch
        binding.swtchAnonimo.setOnCheckedChangeListener { _, isChecked ->
            binding.tvNombre.isEnabled = !isChecked //Activa o desactiva OTRO TEXT, para que no pueda poner el nombre

            // Si anonimo esta pulsado Nombre -> ""
            if (isChecked) {
                binding.tvNombre.setText("")
            }

        }

        // Boton de validar
        binding.btnValidar.setOnClickListener {
            val encuestaCreada = validarEncuesta()

            if (encuestaCreada != null) {
                // Se añade a la lista local para mantener el historial (botones "Cuantas" y "Resumen" local)
                listaEncuestas.add(encuestaCreada)
                // Se envía SÓLO la encuesta recién creada a ResumenActivity
                enviarEncuesta(encuestaCreada)
                resetForm()
                Toast.makeText(this, "Encuesta guardada correctamente", Toast.LENGTH_SHORT).show()
            }
        }

        // Boton de reiniciar todo
        binding.btnReiniciar.setOnClickListener {
            resetFormCompleto()
            listaEncuestas.clear()
            Toast.makeText(this, "Formulario e historial reiniciados", Toast.LENGTH_SHORT).show()
        }

        // Boton de cuantas
        binding.btnCuantas.setOnClickListener {
            val cantidadEncuestas = listaEncuestas.size
            Toast.makeText(this, "Cantidad de encuestas: $cantidadEncuestas", Toast.LENGTH_SHORT).show()
        }

        // Boton de resumen (local)
        binding.btnResumen.setOnClickListener {
            if (listaEncuestas.isNotEmpty()) {
                Toast.makeText(this, "Resumen de encuestas", Toast.LENGTH_SHORT).show()
                binding.tvHistorial.text = listaEncuestas.joinToString("\n") { it.toString() }
            } else {
                Toast.makeText(this, "No hay encuestas guardadas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarEncuesta(): Encuesta? {
        val nombreInput = binding.tvNombre.text.toString().trim()
        val esAnonimo = binding.swtchAnonimo.isChecked
        val nombreUsuario = if (esAnonimo || nombreInput.isEmpty()) "Anónimo" else nombreInput

        // Ejemplo de radio button
        val soId = binding.radioGroup.checkedRadioButtonId
        val soPreferido = when (soId) {
            binding.rdbMac.id -> "Mac"
            binding.rdbWindows.id -> "Windows"
            binding.rdbLinux.id -> "Linux"
            else -> null
        }

        val especialidades = mutableListOf<String>().apply {
            if (binding.checkDAM.isChecked) add("DAM")
            if (binding.checkASIR.isChecked) add("ASIR")
            if (binding.checkDAW.isChecked) add("DAW")
        }

        val horasEstudio = binding.seekBarEstudio.progress

        // Validaciones
        if (!esAnonimo && nombreInput.isEmpty()) {
            Toast.makeText(this, "Por favor, introduce tu nombre o marca 'Anónimo'.", Toast.LENGTH_SHORT).show()
            return null
        }
        if (soPreferido == null) {
            Toast.makeText(this, "Selecciona un sistema operativo preferido.", Toast.LENGTH_SHORT).show()
            return null
        }
        if (especialidades.isEmpty()) {
            Toast.makeText(this, "Selecciona al menos una especialidad.", Toast.LENGTH_SHORT).show()
            return null
        }
        if (!esAnonimo && listaEncuestas.any { it.nombreUsuario.equals(nombreUsuario, ignoreCase = true) }) {
            Toast.makeText(this, "El usuario '$nombreUsuario' ya ha completado una encuesta.", Toast.LENGTH_SHORT).show()
            return null
        }

        // Si todo está correcto, devuelve el objeto Encuesta
        return Encuesta(nombreUsuario, esAnonimo, soPreferido, especialidades, horasEstudio)
    }

    private fun resetFormCompleto() {
        binding.tvNombre.setText("")
        binding.swtchAnonimo.isChecked = false
        binding.radioGroup.clearCheck()
        binding.checkDAM.isChecked = false
        binding.checkASIR.isChecked = false
        binding.checkDAW.isChecked = false
        binding.seekBarEstudio.progress = 0
        binding.tvHorasEstudio.text = "0"
        binding.tvHistorial.text = "Historial:"
    }
    private fun resetForm() {
        binding.tvNombre.setText("")
        binding.swtchAnonimo.isChecked = false
        binding.radioGroup.clearCheck()
        binding.checkDAM.isChecked = false
        binding.checkASIR.isChecked = false
        binding.checkDAW.isChecked = false
        binding.seekBarEstudio.progress = 0
        binding.tvHorasEstudio.text = "0"
    }

    // Envio el objeto encuesta a la 2 Ventana
    private fun enviarEncuesta(encuesta: Encuesta) {
        val intent = Intent(this, ResumenActivity::class.java)
        // Se usa putExtra para enviar un único objeto Parcelable
        intent.putExtra("EXTRA_ENCUESTA", encuesta)
        startActivity(intent)
    }
}

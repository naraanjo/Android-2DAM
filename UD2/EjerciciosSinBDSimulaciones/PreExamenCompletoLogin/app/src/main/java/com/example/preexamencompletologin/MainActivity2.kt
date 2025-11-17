package com.example.preexamencompletologin

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.preexamencompletologin.databinding.ActivityMain2Binding
import com.example.preexamencompletologin.databinding.ActivityMain3Binding
import com.example.preexamencompletologin.model.Persona
import com.example.preexamencompletologin.provider.PersonaProvider

class MainActivity2 : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializo el binding
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        capturarDatos()
        configurarSeekBar()
        configBotones()
    }

    private fun capturarDatos() {

        // Datos enviados desde la ventana 1
        val nombre = intent.getStringExtra("nombre")
        val email = intent.getStringExtra("email");
        val numero = intent.getStringExtra("numero")
        val fecha = intent.getStringExtra("fecha")

        // Los muestro en la UI --> nombre | email
        binding.txNombre.text = nombre
        binding.txEmail.text = email
    }

    // Validar form
    private fun validarForm(): Boolean {

        // Compruebo que el rb este seleccionado
        // Radio
        val generoId = binding.rdbGrupoGenero.checkedRadioButtonId
        val genero = when (generoId) {
            binding.rdbMasculino.id -> "Masculino"
            binding.rdbFemenino.id -> "Femenino"
            else -> null
        }

        if (genero == null) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }

    }

    // Configurar seekBar
    private fun configurarSeekBar() {
        // Valor máximo y actual
        binding.seekBarEdad.max = 100
        binding.seekBarEdad.progress = 0


        // Mostrar valor inicial
        binding.txEdad.text = "Edad: ${binding.seekBarEdad.progress}"


        // Escuchar cambios en la SeekBar
        binding.seekBarEdad.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {


            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Actualiza el TextView con el valor actual
                binding.txEdad.text = "Edad: $progress"
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // (Opcional) algo cuando el usuario empieza a mover
            }


            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // (Opcional) algo cuando el usuario suelta el dedo
            }
        })
    }

    // Configuracion de los botones
    private fun configBotones() {

        // Boton de confirmar
        binding.btnConfirmar.setOnClickListener {

            // TODO
            // Actualizar la recycler, añadiendo a este
            if (validarForm()) {
                Toast.makeText(this, "Registro completado con exito", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Boton de volver
        binding.btnInicio.setOnClickListener {
            finish() // Vuelve atras
        }

        // Boton para abrir una ventana nueva (donde esta la recycler)
        binding.btnMonstrarTodos.setOnClickListener {

            // TODO
            // Abrir la ventana de la recycler
            // Datos enviados desde la ventana 1
            val nombre = intent.getStringExtra("nombre") ?:""
            val email = intent.getStringExtra("email") ?:""
            val numero = intent.getStringExtra("numero") ?:""
            val telefono = numero.toIntOrNull() ?: 0
            val fecha = intent.getStringExtra("fecha") ?:""
            val edad = binding.seekBarEdad.progress
            val generoId = binding.rdbGrupoGenero.checkedRadioButtonId
            val genero = when (generoId) {
                binding.rdbMasculino.id -> "Masculino"
                binding.rdbFemenino.id -> "Femenino"
                else -> null
            }

            val valido = validarForm()
            if (valido) {

                // Creo la persona
               val personaRegistrada = Persona(nombre, fecha, email,
                   telefono, edad, genero!!,R.drawable.ic_launcher_background)

                // Añado la persona al provider
                PersonaProvider.getPersons().add(personaRegistrada)

                // Abro la nueva ventana
                val intent = Intent(this, MainActivity3::class.java)
                startActivity(intent)
            }
        }
    }


}
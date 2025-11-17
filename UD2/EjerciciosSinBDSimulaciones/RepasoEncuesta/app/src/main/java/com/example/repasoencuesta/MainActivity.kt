package com.example.repasoencuesta

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.repasoencuesta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()

        // Inicializo el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarSeekBar()
        configSpinner()
        configBotones()
    }

    // Funcionar para validar que se han seleccionado todos los campos
    private fun validarForm(): Boolean {
        // Radio
        val generoId = binding.rdbGenero.checkedRadioButtonId
        val genero = when (generoId) {
            binding.rdbMasculino.id -> "Masculino"
            binding.rdbFemenino.id -> "Femenino"
            else -> null
        }

        // Desplegable
        var posicionSeleccionada = binding.spinnerEmpresa.selectedItemPosition;


        if (binding.edDNI.text.isEmpty() || binding.edNombre.text.isEmpty() || genero == null ||
            posicionSeleccionada == -1
        ) {

            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        } else {

            Toast.makeText(this, "Registro completado co exito", Toast.LENGTH_SHORT).show()
            return true
        }

    }

    // Configuracion de totos los click
    private fun configBotones() {

        binding.btnRegistrar.setOnClickListener {
            val valido = validarForm()

            // Actualizo el contenido del provider
            if (valido) {
                // Obtengo todos los datos de la persona desde la UI
                val nombre = binding.edNombre.text.toString()
                val dni = binding.edDNI.text.toString()
                val isEstudiante = if (binding.checkEstudiante.isSelected) true else false
                val edad = binding.seekBarEdad.progress
                val empresa = binding.spinnerEmpresa.selectedItem.toString()
                // Radio
                val generoId = binding.rdbGenero.checkedRadioButtonId
                val genero = when (generoId) {
                    binding.rdbMasculino.id -> "Masculino"
                    binding.rdbFemenino.id -> "Femenino"
                    else -> null
                }

                // Paso la persona
                val personaRegistrada = Persona(dni, nombre, isEstudiante, genero!!, edad, empresa)
                abrirVentataPasandoPersona(personaRegistrada)
            }
        }


    }

    private fun abrirVentataPasandoPersona(persona: Persona){

        limpiarForm()
        val intent = Intent(this, MainActivity2::class.java)
        // Envio
        intent.putExtra("1234", persona)
        startActivity(intent)


    }


    // Configuracion de la seekBar
    private fun configurarSeekBar() {
        // Valor máximo y actual
        binding.seekBarEdad.max = 100
        binding.seekBarEdad.progress = 0


        // Mostrar valor inicial
        binding.edadtxv.text = "Edad: ${binding.seekBarEdad.progress}"


        // Escuchar cambios en la SeekBar
        binding.seekBarEdad.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {


            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Actualiza el TextView con el valor actual
                binding.edadtxv.text = "Edad: $progress"
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // (Opcional) algo cuando el usuario empieza a mover
            }


            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // (Opcional) algo cuando el usuario suelta el dedo
            }
        })
    }

    // Rellenar el desplegable con opciones
    private fun configSpinner() {
        val empresas = arrayOf("Google", "Amazon", "IBM")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, empresas)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar el adapter al spinner
        binding.spinnerEmpresa.adapter = adapter;

    }

    private fun limpiarForm(){
        binding.edadtxv.text = "Edad"
        binding.edDNI.setText("")
        binding.edNombre.setText("")
        binding.checkEstudiante.isChecked = false
        binding.rdbGenero.clearCheck()
        binding.seekBarEdad.progress = 50
    }

}
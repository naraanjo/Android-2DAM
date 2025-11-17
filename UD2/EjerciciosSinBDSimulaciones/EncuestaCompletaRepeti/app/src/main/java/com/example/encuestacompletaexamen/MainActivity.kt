package com.example.encuestacompletaexamen

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.encuestacompletaexamen.Model.Persona
import com.example.encuestacompletaexamen.databinding.ActivityMainBinding
import com.example.encuestacompletaexamen.provider.PersonaProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        // Inicializo el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configProgresBar()
        configBotones()
    }

    // Validacion del formulario
    private  fun validarFormulario(): Boolean {

        // Devuleve la id completa o -1 sino se selecciona nada | RADIO GROUP
        val genero = binding.rdGropuGenero.checkedRadioButtonId // Rdb seleccionado

        // Compruebo que todos los campos esten rellenos o seleccionados
        if (binding.edtNombre.text.isEmpty() && binding.edtDni.text.isEmpty() && genero == -1 && binding.spnEmpresa.selectedItem == 0) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

    // Configuracion de la progressBar
    private fun configProgresBar() {
        // Valor máximo y actual
        binding.prgBarEdad.max = 100
        binding.prgBarEdad.progress = 0


        // Mostrar valor inicial
        binding.txvEdad.text = "Edad: ${binding.prgBarEdad.progress}"


        // Escuchar cambios en la SeekBar
        binding.prgBarEdad.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {


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

    // Configuracion de todos los botones
    private fun configBotones(){

        // Boton para contar todos los registros
        binding.btnContar.setOnClickListener {

            // Cantidad de personas en la lista de provider
            val listaPersonas = PersonaProvider.getPersonas()

            // Lo notifico con un toast
            Toast.makeText(this, "Cantidad de personas registradas: ${listaPersonas.size}", Toast.LENGTH_SHORT).show()
        }

        // Boton para registrar a un usuario
        binding.btnRegistrar.setOnClickListener {

            val valido = validarFormulario()

            // Caso de que sea valido
            if(valido){
                // Notificamos el registro exitoso

                Toast.makeText(this, "Registro completado con éxito", Toast.LENGTH_SHORT).show()

                // Valor del rdb
                val genero = binding.rdGropuGenero.checkedRadioButtonId
                val generoSeleccionado = when (genero) {
                    binding.rdbMasculino.id -> "Masculino"
                    binding.rdbFemenino.id -> "Femenino"
                    else -> null
                }
                // CheckBox
                val isEstudiante = if(binding.chkEstudiante.isChecked) true else false

                // Guardo el objeto
                val persona = Persona(
                    binding.edtDni.text.toString(),
                    binding.edtNombre.text.toString(),
                    generoSeleccionado!!,
                    isEstudiante,
                    binding.prgBarEdad.progress,
                    binding.spnEmpresa.selectedItem.toString()
                )


                // Agrego a los datos del provider la nueva persona
                PersonaProvider.getPersonas().add(persona)
                var listaPersona = PersonaProvider.getPersonas()

                // VENTANA 1
                // Actualizo la UI
                binding.txtResumenGlobal.text = "Historial\n: ${listaPersona.joinToString("\n") { persona -> "${persona.nombre} - ${persona.dni} - ${persona.empresa}" }}"
                Toast.makeText(this, "Registro completado con exito", Toast.LENGTH_SHORT).show()

                // VENTANA 2
                // Abro una nueva ventana
                 var intent = Intent(this, MainActivity2::class.java)

                // Envio la persona a la recycler
                intent.putExtra("persona_enviada", persona)
                startActivity(intent)

            }
        }

        // Boto para reinicar todo el contenido de las personas
        binding.btnReiniciar.setOnClickListener {

        }

        // Boton de resumen
        binding.btnResumen.setOnClickListener {

        }
    }


}
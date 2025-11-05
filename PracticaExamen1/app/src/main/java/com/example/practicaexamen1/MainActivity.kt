package com.example.practicaexamen1

<<<<<<< HEAD
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
=======
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.practicaexamen1.databinding.ActivityMainBinding
import com.example.practicaexamen1.model.PersonaModel
import com.example.practicaexamen1.provider.PersonaProvider

class MainActivity : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicio el biding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarSeekBar()

        // btnInicio
        binding.btnIniciar.setOnClickListener {

            // Validacion form
            var valido =validarForm()

            if(valido){
                Toast.makeText(this, "Registro completado con exito", Toast.LENGTH_SHORT).show()

                // Creo un objeto separando las capas
                // Con los datos del form
                val persona = PersonaProvider.crearPersona(binding.txvNombre.text.toString(), binding.txvApellido.text.toString(), binding.skBarEdad.progress,
                    if (binding.rdbMasculino.isChecked) "Masculino" else "Femenino", binding.chckPersonal.isChecked)

                enviarObjetoVentana(persona) // Envio el objeto creado a la siguiente ventana

            }else{
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }


        }

    }

    // Validacion del formulario
    private fun validarForm(): Boolean {
        var completo = true;

        if (binding.txvNombre.text.toString().isEmpty() || binding.txvApellido.text.toString().isEmpty() || binding.txvEdad.text.toString().isEmpty()
            || binding.radioGroup.checkedRadioButtonId==-1) {
            completo = false;
        }

        return completo

    }

    private fun enviarObjetoVentana(persona: PersonaModel){

        if(persona.personal){
            // Abro la ventana del personal
            val intent = Intent(this, MainActivity2::class.java);
            // Envio del objeto
            intent.putExtra("PERSONA_KEY", persona)
            startActivity(intent);
        }else{
            // Abro la ventana del usuario
            val intent = Intent(this, MainActivity3::class.java)
            // Envio el objeto
            intent.putExtra("PERSONA_KEY",persona)
            startActivity(intent) // Inicio la otra activty
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
>>>>>>> 73ccd21430d0574f227ab328534188615ae2d09d
}
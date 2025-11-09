package com.example.gestor
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gestor.database.Persona
import com.example.gestor.database.GestorDatabase
import com.example.gestor.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import com.example.gestor.database.PersonaDao
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val personaDao by lazy {
        GestorDatabase.getDatabase(this).personaDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        configListeners()
    }

    private fun configListeners(){
        binding.btnIniciar.setOnClickListener {
            validarForm()
        }
    }

    private fun validarForm(){
        if(binding.edUsuario.text.toString().isEmpty() || binding.edContraseA.text.toString().isEmpty()){
            Toast.makeText(this, "Por favor rellene todos los campos", Toast.LENGTH_LONG).show()
        }else{
            validarUsuario()
        }
    }

    private fun validarUsuario(){
        lifecycleScope.launch {
            var persona = personaDao.buscarPersonaPorNombre(binding.edUsuario.text.toString())

            if(persona != null){
                if(persona.contrasenia == binding.edContraseA.text.toString()){
                    Toast.makeText(this@MainActivity, "Bienvenido ${persona.nombre}", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@MainActivity, "Contraseña incorrecta",Toast.LENGTH_LONG).show()
                    return@launch
                }
            }else{
                persona = Persona(
                    nombre = binding.edUsuario.text.toString(),
                    contrasenia = binding.edContraseA.text.toString()
                )
                registroAutomatico(persona)
            }

            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            intent.putExtra("persona_key", persona)
            startActivity(intent)
        }
    }

    private fun registroAutomatico(persona: Persona){
        lifecycleScope.launch {

            personaDao.insertarPersona(persona)
            Toast.makeText(this@MainActivity, "Acabas de crearte una nueva cuenta: ${persona.nombre}", Toast.LENGTH_LONG).show()
        }
    }
}
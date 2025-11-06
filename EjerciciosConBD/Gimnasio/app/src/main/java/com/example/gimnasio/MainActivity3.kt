package com.example.gimnasio

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.lifecycleScope
import com.example.gimnasio.Adapter.ClienteAdapter
import com.example.gimnasio.database.Cliente
import com.example.gimnasio.databinding.ActivityMain3Binding
import kotlinx.coroutines.launch
import com.example.gimnasio.database.ClientesDatabase

class MainActivity3 : AppCompatActivity() {

    // Declaro el bindinig
    private lateinit var binding: ActivityMain3Binding

    private lateinit var adapter: ClienteAdapter
    private val clienteDao by lazy {
        ClientesDatabase.getDatabase(this).clienteDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Inicializo el adapter
        adapter = ClienteAdapter(emptyList())

        // Inicio binding
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        configListeners()
    }

    private fun configListeners(){

        // Volver al inicio
        binding.btnInicio.setOnClickListener {
            finish()
        }


        // Registrar al cliente
        binding.btnRegistrar.setOnClickListener {

            confgRegistrar()
        }
    }

    private fun confgRegistrar(){

        // Extraigo el valor de los campos
        val nombre = binding.edNombre.text.toString()
        val contraseña = binding.edContraseA.text.toString()
        val empleo = binding.edEmpleo.text.toString()
        val edad = binding.edEdad.text.toString()

        // Compruebo si estan vacios
        if(nombre.isEmpty() || contraseña.isEmpty() || empleo.isEmpty() || edad.isEmpty()){
            Toast.makeText(this, "Campos incompletos", Toast.LENGTH_LONG).show()
            return // sale
        }
        val cliente = Cliente(nombre = nombre, contraseña = contraseña, empleo = empleo, edad = edad.toInt())

        lifecycleScope.launch {

            // Compruebo si ya eciste
            val clienteExiste = clienteDao.buscarClientePorNombre(cliente.nombre)

            // Sino es null UPDATE
            if(clienteExiste != null){
                // UPDATE
                clienteDao.updateCliente(cliente)
                Toast.makeText(this@MainActivity3, "Cliente actualizado correctamente", Toast.LENGTH_SHORT).show()
            }else{
                // INSERT
                clienteDao.insertCLinete(cliente)
                Toast.makeText(this@MainActivity3, "Cliente registrado correctamente", Toast.LENGTH_SHORT).show()
            }

            // Guardamos en la lista -  recyclerView
            limpiarCampos()


            cargarTodasLosClientes()
        }
    }


    private fun limpiarCampos(){

        binding.edNombre.text?.clear()
        binding.edEdad.text?.clear()
        binding.edContraseA.text?.clear()
        binding.edEmpleo.text?.clear()

        binding.edNombre.requestFocus()
    }
    private fun cargarTodasLosClientes() {
        lifecycleScope.launch {
            val listaClientes = clienteDao.obtenerTodosLosCLientes()
            // Aquí es donde actualizaremos el adaptador.
            // <-- CAMBIO 3: Actualizar el adaptador con la lista de la BD
            adapter.actualizarLista(listaClientes)
            // Toast.makeText(this@MainActivity, "${listaSeries.size} series en la lista", Toast.LENGTH_SHORT).show()
        }
    }
}
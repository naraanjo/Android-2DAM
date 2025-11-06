package com.example.gimnasio

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gimnasio.Adapter.ClienteAdapter
import com.example.gimnasio.database.Cliente
import com.example.gimnasio.database.ClientesDatabase
import com.example.gimnasio.databinding.ActivityMain2Binding
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMain2Binding

    private lateinit var adapter: ClienteAdapter

    private val clienteDao by lazy {
        ClientesDatabase.getDatabase(this).clienteDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        // Inicializo el binding
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        confRecycler()
        cargarTodasLosClientes()
        confListeners()

    }

    private fun confListeners() {
        binding.btnVolver.setOnClickListener {
            finish()
        }

        binding.btnDetalles.setOnClickListener {
            // Abro nueva activity
            if (binding.edNombre.text.toString().isEmpty()) {
                Toast.makeText(this, "Rellene el nombre", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "Enviando...", Toast.LENGTH_LONG).show()

                enviarObjeto()

            }
        }

        binding.btnEliminar.setOnClickListener {

            eliminarPorNombre()
        }
    }

    private fun confRecycler() {

        adapter = ClienteAdapter(emptyList())
        binding.rvCliente.layoutManager = LinearLayoutManager(this)
        binding.rvCliente.adapter = adapter


    }

    private fun enviarObjeto() {

        var cliente: Cliente? = Cliente(nombre = "", contraseña = "", empleo = "", edad = 0, )
        // Buscamos por id
        lifecycleScope.launch {
            val nombre = binding.edNombre.text.toString()
            cliente = clienteDao.buscarClientePorNombre(nombre = nombre)


            val intent = Intent(this@MainActivity2, MainActivity4::class.java)
            intent.putExtra("CLIENTE_KEY", cliente)
            startActivity(intent)
        }

    }

    private  fun eliminarPorNombre(){

        lifecycleScope.launch {
            // Extraigo el objeto

            var cliente = clienteDao.buscarClientePorNombre(binding.edNombre.text.toString())

            if(cliente!= null){
                clienteDao.eliminarCliente(cliente)
                Toast.makeText(this@MainActivity2, "Cliente eliminado", Toast.LENGTH_LONG).show()
                cargarTodasLosClientes()

            }else{
                Toast.makeText(this@MainActivity2, "Cliente no encontrado", Toast.LENGTH_LONG).show()
            }
        }
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

    override fun onResume() {
        super.onResume()
        cargarTodasLosClientes()
    }
}
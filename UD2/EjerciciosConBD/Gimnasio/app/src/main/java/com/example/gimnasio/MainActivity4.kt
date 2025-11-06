package com.example.gimnasio

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gimnasio.Adapter.ClienteAdapter
import com.example.gimnasio.database.Cliente
import com.example.gimnasio.database.ClientesDatabase
import com.example.gimnasio.databinding.ActivityMain4Binding
import kotlinx.coroutines.launch

class MainActivity4 : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMain4Binding


    private val clienteDao by lazy {
        ClientesDatabase.getDatabase(this).clienteDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        actualizarUI()

        binding.btnActualizar.setOnClickListener {
            var cliente = recogerObjetoCliente()
            actualizarCliente(cliente)
        }
    }


    private fun recogerObjetoCliente(): Cliente? {
        val key = "CLIENTE_KEY" // CAMBIAR

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, Cliente::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(key)
        }
    }

    private fun actualizarUI(){
        val cliente = recogerObjetoCliente()

        binding.edNombre.setText(cliente?.nombre.toString())
        binding.edEdad.setText(cliente?.edad.toString())
        binding.edContraseA.setText(cliente?.contraseña)
        binding.edEmpleo.setText(cliente?.empleo)

    }

    private fun actualizarCliente(clienteOriginal: Cliente?) {
        if (clienteOriginal == null) {
            Toast.makeText(this, "Error: cliente no encontrado", Toast.LENGTH_SHORT).show()
            return
        }

        val clienteActualizado = clienteOriginal.copy(
            nombre = binding.edNombre.text.toString(),
            edad = binding.edEdad.text.toString().toIntOrNull() ?: 0,
            contraseña = binding.edContraseA.text.toString(),
            empleo = binding.edEmpleo.text.toString()
        )

        lifecycleScope.launch {
            clienteDao.updateCliente(clienteActualizado)
            Toast.makeText(this@MainActivity4, "Cliente actualizado", Toast.LENGTH_LONG).show()
            finish() // 🔁 vuelve a la lista
        }
    }



}
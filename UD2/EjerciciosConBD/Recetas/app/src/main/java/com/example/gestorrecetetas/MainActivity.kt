package com.example.gestorrecetetas

import android.os.Bundle
import android.widget.Toast
import com.example.gestorrecetetas.database.RecetasDatabase
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestorrecetetas.adapter.RecetaAdapter
import com.example.gestorrecetetas.database.Receta
import com.example.gestorrecetetas.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // Declaro el binding
    private lateinit var binding: ActivityMainBinding

    // Declaro el adaptador
    private lateinit var adapter: RecetaAdapter

    // DAO
    private val recetaDao by lazy {
        RecetasDatabase.getDatabase(this).recetaDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        // Inflamos el layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarRecycler()

        configurarListeners()
        cargarTodasLasRecetas()

    }


    suspend fun eliminar() {
        recetaDao.eliminarTodasLasRecetas()
    }

    // Cofiguracion de los listeners
    private fun configurarListeners() {

        binding.btnBuscar.setOnClickListener {
            buscarReceta()
        }

        binding.btnGuardar.setOnClickListener {
            guardarReceta()
        }
        binding.btnEliminar.setOnClickListener {
            eliminarReceta()
        }
    }

    private fun configurarRecycler() {

        // Inicio el adapter co una lista vacia
        adapter = RecetaAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Asigamos el adaptador al recycler
        binding.recyclerView.adapter = adapter
    }

    // CRUD
    private fun guardarReceta() {
        // Recogemos todos los datos de la UI
        val nombre = binding.etNombre.text.toString()
        val dificultad = binding.etDificultad.text.toString()
        val precio = binding.etPrecio.text.toString()

        // Validaciones de todos los campos
        if (nombre.isEmpty() || dificultad.isEmpty() || precio.isEmpty()) {
            // Toast informativo
            Toast.makeText(this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Creo un objeto  receta con los datos de la UI
        val receta = Receta(nombre = nombre, dificultad = dificultad, precio = precio.toInt())

        // Corrutina con la operacion DB
        lifecycleScope.launch {
            // Buscamos si la receta existe
            val recetaExiste = recetaDao.buscarReceta(id = receta.id)

            if (recetaExiste != null) {
                // Si existe, actualizamos
                recetaDao.actualizarReceta(receta)
                Toast.makeText(this@MainActivity, "Receta actualizada", Toast.LENGTH_LONG).show()
            } else {
                // Si no existe, la insertamos
                recetaDao.insertarReceta(receta)
                Toast.makeText(this@MainActivity, "Receta insertada", Toast.LENGTH_LONG).show()
            }

            // Limpiamos la UI y recargamos la lista
            limpiarCampos()
            cargarTodasLasRecetas()
        }


    }

    private fun buscarReceta() {

        val nombre = binding.etNombre.text.toString()

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Introduce un nombre para buscar", Toast.LENGTH_SHORT).show()
            return
        }

        // Operacion de la bd
        lifecycleScope.launch {
            val receta = recetaDao.buscarRecetaPorNombre(nombre)

            // Rellenamos los campos con los valores encontrados
            if (receta != null) {
                binding.etDificultad.setText(receta.dificultad)
                binding.etPrecio.setText(receta.precio.toString())

                Toast.makeText(this@MainActivity, "Receta encontrada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "La receta no se ha encotrado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun eliminarReceta() {
        val nombre = binding.etNombre.text.toString()
        if (nombre.isBlank()) {
            Toast.makeText(this, "Introduce un nombre para eliminar", Toast.LENGTH_SHORT).show()
            return

        }

        // Operacion de la bd corrutina
        lifecycleScope.launch {

            val receta = recetaDao.buscarRecetaPorNombre(nombre)

            if (receta != null) {
                recetaDao.deleteReceta(receta)
                Toast.makeText(this@MainActivity, "Receta Eliminada", Toast.LENGTH_SHORT).show()

                limpiarCampos()
                cargarTodasLasRecetas()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "La receta no se ha encotrado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun cargarTodasLasRecetas() {
        lifecycleScope.launch {
            val listaSeries = recetaDao.obtenerTodasLasRecetas()
            // Aquí es donde actualizaremos el adaptador.
            // <-- CAMBIO 3: Actualizar el adaptador con la lista de la BD
            adapter.actualizarLista(listaSeries)
        }
    }

    private fun limpiarCampos() {
        binding.etNombre.text?.clear()
        binding.etDificultad.text?.clear()
        binding.etPrecio.text?.clear()
        binding.etNombre.requestFocus() // Ponemos el foco de nuevo en el nombre
    }
}

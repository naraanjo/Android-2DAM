package com.example.gestor

import AdapterTarea
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestor.database.GestorDatabase
import com.example.gestor.database.Persona
import com.example.gestor.database.Tarea
import com.example.gestor.databinding.ActivityMain2Binding
import com.example.gestor.databinding.DialogNuevaTareaBinding
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var adapter: AdapterTarea
    private val tareaDao by lazy { GestorDatabase.getDatabase(this).tareaDao() }

    private var fechaSeleccionada: String = "" // Guardar fecha actual seleccionada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        actualizarUI()
        configBotones()
    }

    private fun configBotones() {
        binding.fabAddTask.setOnClickListener {
            val persona = recogerObjetoPersona()
            if (persona == null) {
                Toast.makeText(this, "Error: no se encontró la persona", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dialogBinding = DialogNuevaTareaBinding.inflate(layoutInflater)

            // Selector de fecha
            dialogBinding.etFecha.setOnClickListener {
                val calendario = Calendar.getInstance()
                val year = calendario.get(Calendar.YEAR)
                val month = calendario.get(Calendar.MONTH)
                val day = calendario.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    val fecha = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                    dialogBinding.etFecha.setText(fecha)
                }, year, month, day).show()
            }

            // Selector de hora
            dialogBinding.etHora.setOnClickListener {
                val calendario = Calendar.getInstance()
                val hora = calendario.get(Calendar.HOUR_OF_DAY)
                val minuto = calendario.get(Calendar.MINUTE)

                TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                    val horaTxt = String.format("%02d:%02d", selectedHour, selectedMinute)
                    dialogBinding.etHora.setText(horaTxt)
                }, hora, minuto, true).show()
            }

            val dialog = AlertDialog.Builder(this)
                .setTitle("Nueva tarea")
                .setView(dialogBinding.root)
                .setPositiveButton("Guardar", null)
                .setNegativeButton("Cancelar") { d, _ -> d.dismiss() }
                .create()

            dialog.setOnShowListener {
                val btnGuardar = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

                btnGuardar.setOnClickListener {
                    val titulo = dialogBinding.etTitulo.text.toString().trim()
                    val descripcion = dialogBinding.etDescripcion.text.toString().trim()
                    val fecha = dialogBinding.etFecha.text.toString().trim()
                    val hora = dialogBinding.etHora.text.toString().trim()

                    if (titulo.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                        Toast.makeText(this, "Por favor completa título, fecha y hora", Toast.LENGTH_SHORT).show()
                    } else {
                        lifecycleScope.launch {
                            val nuevaTarea = Tarea(
                                titulo = titulo,
                                descripcion = descripcion,
                                fecha = fecha,
                                hora = hora,
                                idPersona = persona.id
                            )
                            tareaDao.insertar(nuevaTarea)

                            // Si la tarea corresponde al día seleccionado, la mostramos
                            if (fecha == fechaSeleccionada) {
                                val tareas = tareaDao.getTareasPorFecha(persona.id, fecha)
                                adapter.actualizarLista(tareas)
                            }
                        }
                        dialog.dismiss()
                    }
                }
            }

            dialog.show()
        }

        // Volver atrás
        binding.btnProfile.setOnClickListener { finish() }
    }

    private fun recogerObjetoPersona(): Persona? {
        val key = "persona_key"
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, Persona::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(key)
        }
    }

    private fun actualizarUI() {
        val persona = recogerObjetoPersona() ?: return
        confRecycler(persona)
    }

    private fun confRecycler(persona: Persona) {
        adapter = AdapterTarea(mutableListOf()) { tareaEliminar ->
            lifecycleScope.launch {
                tareaDao.eliminar(tareaEliminar)
                val tareas = tareaDao.getTareasPorFecha(persona.id, fechaSeleccionada)
                adapter.actualizarLista(tareas)
                Toast.makeText(this@MainActivity2, "Tarea eliminada", Toast.LENGTH_SHORT).show()
            }
        }

        binding.rvTareas.layoutManager = LinearLayoutManager(this)
        binding.rvTareas.adapter = adapter

        // Configurar el calendario
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            lifecycleScope.launch {
                val tareas = tareaDao.getTareasPorFecha(persona.id, fechaSeleccionada)
                adapter.actualizarLista(tareas)
            }
        }

        // Mostrar tareas del día actual al abrir
        val calendario = Calendar.getInstance()
        fechaSeleccionada = String.format(
            "%04d-%02d-%02d",
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH) + 1,
            calendario.get(Calendar.DAY_OF_MONTH)
        )

        lifecycleScope.launch {
            val tareasHoy = tareaDao.getTareasPorFecha(persona.id, fechaSeleccionada)
            adapter.actualizarLista(tareasHoy)
        }
    }
}

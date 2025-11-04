package com.example.practicaexamen1.provider

import com.example.practicaexamen1.model.TareaModel

object TareaProvider {
    val listaTareas: MutableList<TareaModel> = mutableListOf(
        TareaModel("Comprar alimentos", "Comprar frutas, verduras y pan"),
        TareaModel("Estudiar Kotlin", "Repasar clases de Android Studio"),
        TareaModel("Hacer ejercicio", "Correr 30 minutos en el parque"),
        TareaModel("Llamar a mamá", "Preguntar cómo está y charlar un rato"),
        TareaModel("Enviar correo", "Mandar informe semanal al jefe"),
        TareaModel("Limpiar casa", "Ordenar la habitación y limpiar el baño"),
        TareaModel("Leer libro", "Terminar el capítulo 5 del libro de Kotlin"),
        TareaModel("Pagar facturas", "Electricidad, agua e internet"),
        TareaModel("Preparar cena", "Cocinar pasta con ensalada"),
        TareaModel("Organizar escritorio", "Ordenar papeles y material de estudio")
    )

    // Opcional: método para agregar nuevas tareas
    fun agregarTarea(tarea: TareaModel) {
        listaTareas.add(tarea)
    }

    fun obtenerTareas(): List<TareaModel> {
        return listaTareas
    }
}
package com.example.fit.provider

import com.example.fit.model.DiaModel

// Proporciona los datos a la app
// Patron Singleton, unci proveedor de datos
object DiaProvider {

    private val dias = mutableListOf(
        DiaModel("Lunes", 200),
        DiaModel("Martes", 200),
        DiaModel("Miercoles", 200),
        DiaModel("Jueves", 200),
        DiaModel("Viernes", 200),
        DiaModel("Sabado", 200),
        DiaModel("Domingo", 200)
    )

    // Funcion publica para obtener todos los dias
    fun getDias(): MutableList<DiaModel>{
        return dias
    }

}
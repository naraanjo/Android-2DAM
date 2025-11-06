package com.example.fit.provider

import com.example.fit.R
import com.example.fit.model.PlatoModel

object PlatoProvider {

    private val platos = mutableListOf(
        // Lunes
        PlatoModel("Macarrones con atún y huevo", "Cantidad: 1 huevo", R.drawable.macarrones, "Comida"),
        PlatoModel("Pechuga de pollo con patatas (AirFryer)", "2 pechugas de pollo", R.drawable.pechuga, "Cena"),
        PlatoModel("2 Huevos fritos con patatas (AirFryer)", "Con jamón o atún", R.drawable.huevos, "Cena o Comida"),

        // Martes
        PlatoModel("Chuleta con patatas (AirFryer)", "1 chuleta, patatas y 1 huevo", R.drawable.chuleta, "Comida"),
        PlatoModel("Macarrones con atún y huevo", "Cantidad: 1 huevo", R.drawable.macarrones, "Cena"),
        PlatoModel("2 Huevos fritos con patatas (AirFryer)", "Con jamón o atún", R.drawable.huevos, "Cena o Comida"),

        // Miércoles
        PlatoModel("Pechuga de pollo con patatas (AirFryer)", "2 pechugas de pollo", R.drawable.pechuga, "Comida"),
        PlatoModel("Macarrones con atún y huevo", "Cantidad: 1 huevo", R.drawable.macarrones, "Cena"),
        PlatoModel("Arroz con huevo frito y atún", "1 huevo frito", R.drawable.arroz, "Comida o cena"),

        // Jueves
        PlatoModel("Macarrones con atún y huevo", "Cantidad: 1 huevo", R.drawable.macarrones, "Cena"),
        PlatoModel("Patata cocida con atún ", "+1 huevo frito ",R.drawable.patataasada, "Comida"),
        PlatoModel("2 Huevos fritos con patatas (AirFryer)", "Con jamón o atún", R.drawable.huevos, "Cena o Comida"),

        // Viernes
        PlatoModel("Pechuga de pollo con patatas (AirFryer)", "2 pechugas de pollo", R.drawable.pechuga, "Comida"),
        PlatoModel("Macarrones con atún y huevo", "Cantidad: 1 huevo", R.drawable.macarrones, "Cena"),
        PlatoModel("Arroz con huevo frito y atún", "1 huevo frito", R.drawable.arroz, "Comida o cena"),

        // Sábado
        PlatoModel("1 hamburguesa con patatas (AirFryer)", "Patatas: En la cosory", R.drawable.hamburguesa, "Comida"),
        PlatoModel("Macarrones con atún y huevo", "Cantidad: 1 huevo", R.drawable.macarrones, "Cena"),
        PlatoModel("2 Huevos fritos con patatas (AirFryer)", "Con jamón o atún", R.drawable.huevos, "Cena o Comida"),

        // Domingo
        PlatoModel("Arroz con huevo frito y atún", "1 huevo frito", R.drawable.arroz, "Comida"),
        PlatoModel("Macarrones con atún y huevo", "Cantidad: 1 huevo", R.drawable.macarrones, "Cena"),
        PlatoModel("2 Huevos fritos con patatas (AirFryer)", "Con jamón o atún", R.drawable.huevos, "Cena o Comida"),
    )

    // 🔹 Devuelve todos los platos
    fun getPlatos(): MutableList<PlatoModel> = platos

    // 🔹 Devuelve los 3 platos correspondientes a un día
    fun getPlatosPorDia(dia: String): List<PlatoModel> {
        val index = when (dia.lowercase()) {
            "lunes" -> 0
            "martes" -> 3
            "miercoles", "miércoles" -> 6
            "jueves" -> 9
            "viernes" -> 12
            "sabado", "sábado" -> 15
            "domingo" -> 18
            else -> -1
        }

        return if (index >= 0 && index + 2 < platos.size) {
            platos.subList(index, index + 3)
        } else {
            emptyList() // si no se encuentra el día, devuelve lista vacía
        }
    }
}

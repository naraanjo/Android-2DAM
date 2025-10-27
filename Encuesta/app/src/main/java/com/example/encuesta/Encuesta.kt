package com.example.encuesta

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Encuesta(
    val nombreUsuario: String,          // Nombre del usuario o "Anónimo"
    val esAnonimo: Boolean,             // Estado del Switch
    val soPreferido: String,            // Sistema operativo seleccionado (Mac, Windows, Linux)
    val especialidades: List<String>,   // Lista de CheckBoxes seleccionados (DAM, ASIR, DAW)
    val horasEstudio: Int               // Valor de la SeekBar
) : Parcelable {


    // Sobreescribe el método toString() para un formato legible en el resumen
    override fun toString(): String {
        val especialidadesStr =
            if (especialidades.isEmpty()) "Ninguna" else especialidades.joinToString()
        return "Usuario: $nombreUsuario | $soPreferido | $especialidadesStr | Estudio: ${horasEstudio}h"
    }
}


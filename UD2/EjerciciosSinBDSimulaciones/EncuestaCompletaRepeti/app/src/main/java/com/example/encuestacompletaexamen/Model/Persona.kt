package com.example.encuestacompletaexamen.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Defino la clase persona
@Parcelize
data class Persona(

    val dni: String,
    val nombre:String,
    val genero:String,
    val isEstudiante: Boolean,
    val edad:Int,
    val empresa: String
) : Parcelable {
}
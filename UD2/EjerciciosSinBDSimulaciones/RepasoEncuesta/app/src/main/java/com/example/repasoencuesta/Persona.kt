package com.example.repasoencuesta

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Persona(

    var dni:String,
    var nombre: String,
    var isEstudainte: Boolean,
    var genero: String,
    var edad: Int,
    var empresa: String

) : Parcelable {
}
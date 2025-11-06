package com.example.practicaexamen1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PersonaModel(
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val genero: String,
    val personal: Boolean
) : Parcelable {
}
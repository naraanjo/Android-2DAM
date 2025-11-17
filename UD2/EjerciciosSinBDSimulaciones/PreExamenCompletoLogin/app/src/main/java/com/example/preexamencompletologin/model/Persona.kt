package com.example.preexamencompletologin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Persona(
    var nombre: String,
    var fecha:String,
    var email:String,
    var telefono: Int,
    var edad: Int,
    var genero: String,
    var imagen: Int

) : Parcelable {
}
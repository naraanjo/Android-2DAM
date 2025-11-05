package com.example.practicaexamen1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
 data class ActividadModel( var nombre:String, var dificultad: String) : Parcelable {

}
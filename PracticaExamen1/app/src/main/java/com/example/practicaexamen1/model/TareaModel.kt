package com.example.practicaexamen1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TareaModel (val nombre: String, val descripcion: String): Parcelable {
}
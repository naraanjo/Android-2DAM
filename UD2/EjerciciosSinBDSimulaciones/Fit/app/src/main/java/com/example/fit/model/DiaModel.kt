package com.example.fit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Nombre del dia y kcals necesarias
@Parcelize
data class DiaModel (val nombre: String, val kcal: Int): Parcelable {
}
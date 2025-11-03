package com.example.fit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PlatoModel(
    val nombre:String,
    val infoAdicional: String,
    val foto:Int,
    val tipoComida: String
): Parcelable {
}
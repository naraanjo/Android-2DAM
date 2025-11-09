package com.example.gestor.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "persona_tabla")
data class Persona (
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,

    @ColumnInfo(name="nombre")
    val nombre: String,

    @ColumnInfo(name="contrasenia")
    val contrasenia: String,
) : Parcelable {

}
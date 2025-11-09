package com.example.gestor.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Define la estructura de la tabla Tarea
@Parcelize
@Entity(tableName = "tarea_tabla")
data class Tarea(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    @ColumnInfo(name="titulo")
    val titulo:String,

    @ColumnInfo(name="descripcion")
    val descripcion:String,

    @ColumnInfo(name="fecha")
    val fecha:String,

    @ColumnInfo(name="hora")
    val hora:String,

    // CLAVE FORÁNEA: Define la relación 1:N con Persona
    @ColumnInfo(name="id_persona")
    val idPersona: Int,

    ) : Parcelable
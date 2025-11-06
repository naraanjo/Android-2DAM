package com.example.gestorrecetetas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Clase que define la estructura de la tabla

@Entity(tableName = "receta_tabla")
class Receta(

    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    @ColumnInfo(name="nombre")
    val nombre: String,

    @ColumnInfo(name= "dificultad")
    val dificultad: String,

    @ColumnInfo(name= "precio")
    val precio : Int
){


}
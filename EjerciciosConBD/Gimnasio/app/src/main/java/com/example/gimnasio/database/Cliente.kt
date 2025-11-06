package com.example.gimnasio.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tabla_cliente")
data class Cliente(

    // Pk autogenerada
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name="nombre")
    val nombre: String,

    @ColumnInfo(name="contraseña")
    val contraseña: String,

    @ColumnInfo(name="empleo")
    val empleo: String,

    @ColumnInfo(name="edad")
    val edad: Int


) : Parcelable
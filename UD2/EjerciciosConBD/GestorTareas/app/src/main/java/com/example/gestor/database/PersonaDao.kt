package com.example.gestor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonaDao {

    @Insert
    suspend fun insertarPersona(persona: Persona)

    @Update
    suspend fun actualizarPersona(persona: Persona)

    @Query("Select * from persona_tabla where nombre = :nombre")
    suspend fun buscarPersonaPorNombre(nombre: String): Persona?


}
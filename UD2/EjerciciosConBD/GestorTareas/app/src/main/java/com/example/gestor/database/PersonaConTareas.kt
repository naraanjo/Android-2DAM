package com.example.gestor.database

import androidx.room.Embedded
import androidx.room.Relation

data class PersonaConTareas(
    //  Entidad principal (el lado UNO de la relación)
    @Embedded val persona: Persona,

    //  Lista de entidades relacionadas (el lado MUCHOS de la relación)
    @Relation(
        parentColumn = "id", // Campo de la entidad Persona (Persona.id)
        entityColumn = "id_persona" // Campo en la entidad Tarea que apunta a Persona (Tarea.idPersona)
    )
    val tareas: MutableList<Tarea>
)
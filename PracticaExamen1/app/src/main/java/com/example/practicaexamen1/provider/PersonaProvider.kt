package com.example.practicaexamen1.provider

import com.example.practicaexamen1.model.PersonaModel

object PersonaProvider {

    fun crearPersona(nombre: String, apellido: String, edad: Int, genero: String, esPersonal: Boolean): PersonaModel {
        return PersonaModel(nombre, apellido, edad, genero, esPersonal)
    }
}
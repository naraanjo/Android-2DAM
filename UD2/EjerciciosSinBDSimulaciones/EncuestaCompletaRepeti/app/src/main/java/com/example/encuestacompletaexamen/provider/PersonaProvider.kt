package com.example.encuestacompletaexamen.provider
import com.example.encuestacompletaexamen.Model.Persona

// Proporciona los datos a la app
// Patrón Singleton, único proveedor de datos
object PersonaProvider {

    private val personas = mutableListOf(
        Persona("12345678A", "Juan Pérez", "Masculino", true, 25, "Google"),
        Persona("23456789B", "María López", "Femenino", false, 30, "Microsoft"),
        Persona("34567890C", "Carlos García", "Masculino", true, 22, "Amazon"),
        Persona("45678901D", "Laura Sánchez", "Femenino", true, 28, "Facebook"),
        Persona("56789012E", "Miguel Torres", "Masculino", false, 35, "Apple"),
        Persona("67890123F", "Ana Fernández", "Femenino", true, 27, "Tesla"),
        Persona("78901234G", "David Ruiz", "Masculino", false, 40, "OpenAI")
    )

    // Función pública para obtener todas las personas
    fun getPersonas(): MutableList<Persona> {
        return personas
    }
}

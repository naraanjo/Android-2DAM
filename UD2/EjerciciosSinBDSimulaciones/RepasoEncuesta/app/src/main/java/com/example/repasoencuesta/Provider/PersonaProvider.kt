import com.example.repasoencuesta.Persona

object  PersonaProvider {

    private val personas: MutableList<Persona> = mutableListOf(
        Persona("12345678A", "Ana", true, "Femenino", 20, "Google"),
        Persona("23456789B", "Luis", false, "Masculino", 25, "Amazon"),
        Persona("34567890C", "Marta", true, "Femenino", 22, "IBM"),
        Persona("45678901D", "Carlos", false, "Masculino", 30, "Microsoft"),
        Persona("56789012E", "Lucía", true, "Femenino", 21, "Facebook"),
        Persona("67890123F", "Javier", false, "Masculino", 28, "Apple"),
        Persona("78901234G", "Sofía", true, "Femenino", 19, "Netflix"),
        Persona("89012345H", "Miguel", false, "Masculino", 35, "Tesla"),
        Persona("90123456I", "Laura", true, "Femenino", 23, "Spotify"),
        Persona("01234567J", "David", false, "Masculino", 40, "Amazon")
    )

    fun getPersons(): MutableList<Persona> = personas
}

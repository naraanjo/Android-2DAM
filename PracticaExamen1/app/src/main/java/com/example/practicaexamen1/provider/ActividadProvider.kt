
import com.example.practicaexamen1.model.ActividadModel

object ActividadProvider {

    val actividades: MutableList<ActividadModel> = mutableListOf(
        ActividadModel("Hacer ejercicio", "Ir al gimnasio 1 hora"),
        ActividadModel("Estudiar Kotlin", "Repasar RecyclerView y ViewBinding"),
        ActividadModel("Leer", "Terminar el libro de Android Jetpack"),
        ActividadModel("Limpiar", "Ordenar habitación y escritorio"),
        ActividadModel("Cocinar", "Preparar pasta con salsa de tomate"),
        ActividadModel("Pasear al perro", "Dar una vuelta de 30 minutos"),
        ActividadModel("Escuchar música", "Relajarse con lo-fi beats"),
        ActividadModel("Meditar", "Practicar 10 minutos de respiración"),
        ActividadModel("Llamar a un amigo", "Ponerse al día con Carlos"),
        ActividadModel("Ver serie", "Ver un capítulo de tu serie favorita")
    )


    fun obtenerActividades(): MutableList<ActividadModel>{
        return actividades
    }
}

package com.example.preexamencompletologin.provider

import com.example.preexamencompletologin.R
import com.example.preexamencompletologin.model.Persona


object  PersonaProvider {

    private val personas : MutableList<Persona> = mutableListOf(
        Persona("Ana", "01/01/2004", "ana@gmail.com", 12345678, 20, "Femenino", R.drawable.persona2),
        Persona("Luis", "02/02/1999", "luis@gmail.com", 23456789, 25, "Masculino", R.drawable.persona4),
        Persona("Marta", "03/03/2002", "marta@gmail.com", 34567890, 22, "Femenino", R.drawable.outline_1x_mobiledata_24),
        Persona("Carlos", "04/04/1994", "carlos@gmail.com", 45678901, 30, "Masculino" , R.drawable.outline_1x_mobiledata_24),
        Persona("Lucía", "05/05/2003", "lucia@gmail.com", 56789012, 21, "Femenino" , R.drawable.outline_1x_mobiledata_24),
        Persona("Javier", "06/06/1996", "javier@gmail.com", 67890123, 28, "Masculino" , R.drawable.outline_1x_mobiledata_24),
        Persona("Sofía", "07/07/2005", "sofia@gmail.com", 78901234, 19, "Femenino" , R.drawable.outline_1x_mobiledata_24),
        Persona("Miguel", "08/08/1989", "miguel@gmail.com", 89012345, 35, "Masculino" , R.drawable.outline_1x_mobiledata_24),
        Persona("Laura", "09/09/2001", "laura@gmail.com", 90123456, 23, "Femenino" , R.drawable.outline_1x_mobiledata_24),
        Persona("David", "10/10/1984", "david@gmail.com", 1234567, 40, "Masculino" , R.drawable.outline_1x_mobiledata_24)
    )


    fun getPersons(): MutableList<Persona> = personas as MutableList<Persona>
}

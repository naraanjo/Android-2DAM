package com.example.gestor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. Anotación @Database:
//    - entities: Lista de todas las clases @Entity que usará esta BD.
//    - version: Número de versión. Increméntalo si cambias el esquema (ej. añades una columna).
@Database(entities = [Persona::class, Tarea::class], version = 2)
abstract class GestorDatabase : RoomDatabase() {

    // 2. Método abstracto para obtener el DAO:
    //    Room generará el cuerpo de esta función por nosotros.
    abstract fun personaDao(): PersonaDao
    abstract fun tareaDao(): TareaDao

    //    Esto nos permite acceder al método getDatabase sin necesidad de crear una instancia
    //    de GestorDatabase. Usamos un patrón Singleton para tener una única instancia de la BD en toda la app.
    companion object {
        // La anotación @Volatile asegura que el valor de INSTANCE sea siempre el más actualizado
        // y visible para todos los hilos de ejecución.
        @Volatile
        private var INSTANCE: GestorDatabase? = null

        fun getDatabase(context: Context): GestorDatabase {
            // Si ya existe una instancia, la devolvemos.
            // Si no, creamos la base de datos.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GestorDatabase::class.java,
                    "gestor_database3" // Nombre del archivo de la base de datos
                ).build()
                INSTANCE = instance
                // Devolvemos la instancia recién creada
                instance
            }
        }
    }
}

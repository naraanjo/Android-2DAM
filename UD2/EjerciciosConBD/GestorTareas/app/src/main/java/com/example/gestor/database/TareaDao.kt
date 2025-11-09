package com.example.gestor.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

/**
 * Data Access Object para la entidad Tarea.
 * Contiene los métodos necesarios para interactuar con la tabla 'tarea_tabla'.
 */
@Dao
interface TareaDao {

    /**
     * Inserta una nueva tarea. Si hay conflicto, reemplaza la tarea existente.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(tarea: Tarea)

    /**
     * Obtiene todas las tareas asociadas a una persona específica (usando la clave foránea).
     * Nota: Usamos @Transaction y el objeto PersonaConTareas para obtener la relación 1:N.
     */
    @Transaction
    @Query("SELECT * FROM persona_tabla WHERE id = :idPersona")
    fun getTareasPorPersona(idPersona: Int): MutableList<PersonaConTareas>

    /**
     * Obtiene todas las tareas.
     */
    @Query("SELECT * FROM tarea_tabla ORDER BY id DESC")
    fun getTodasLasTareas(): LiveData<List<Tarea>>

    /**
     * Elimina una tarea específica por su ID.
     */
    @Query("DELETE FROM tarea_tabla WHERE id = :tareaId")
    suspend fun eliminarTareaPorId(tareaId: Int)



    @Query("SELECT * FROM tarea_tabla WHERE id_persona = :idPersona")
    suspend fun getTareasPorPersonaDirecto(idPersona: Int): MutableList<Tarea>



    /**
     * Elimina todas las tareas de la base de datos.
     */
    @Query("DELETE FROM tarea_tabla")
    suspend fun eliminarTodasLasTareas()
}
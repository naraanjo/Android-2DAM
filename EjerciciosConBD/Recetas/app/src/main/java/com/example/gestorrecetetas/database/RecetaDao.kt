package com.example.gestorrecetetas.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Es el que trabaja con la BD

@Dao
interface RecetaDao {

    // Operacion CRUD
    @Insert
    suspend fun insertarReceta(receta: Receta)

    // Buscar una receta por Id
    @Query("Select * from receta_tabla where id like :id")
    suspend fun buscarReceta(id: Int): Receta?

    // Buscar una receta por Id
    @Query("Select * from receta_tabla where nombre like :nombre")
    suspend fun buscarRecetaPorNombre(nombre: String): Receta?

    @Query("DELETE FROM receta_tabla")
    suspend fun eliminarTodasLasRecetas()



    // Actualizo una receta por la pK
    @Update
    suspend fun actualizarReceta(receta: Receta)

    // Delete
    @Delete
    suspend fun deleteReceta(receta: Receta)

    // Devuelvo todas las recetas ordenadas por nombre
    @Query("SELECT * FROM receta_tabla order by nombre ASC")
    suspend fun obtenerTodasLasRecetas(): List<Receta>
}
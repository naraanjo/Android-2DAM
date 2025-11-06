package com.example.gimnasio.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Contacto con la bd
@Dao
interface ClienteDao {

    // Insert de clientes
    @Insert
    suspend fun insertCLinete(cliente: Cliente)

    // Buscar por nombre
    @Query("SELECT * FROM tabla_cliente WHERE nombre = :nombre LIMIT 1")
    suspend fun buscarClientePorNombre(nombre:String): Cliente

    // Actualizar a un cliente
    @Update
    suspend fun updateCliente(cliente: Cliente)

    // Cragar todos los clientes
    @Query("SELECT * FROM tabla_cliente ORDER BY nombre ASC")
    suspend fun obtenerTodosLosCLientes():List<Cliente>

    // Eliminar cliente
    @Delete
    suspend fun eliminarCliente(cliente: Cliente)

}
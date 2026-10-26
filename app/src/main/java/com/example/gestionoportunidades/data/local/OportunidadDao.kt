package com.example.gestionoportunidades.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OportunidadDao {
    @Query("SELECT * FROM oportunidades ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<OportunidadEntity>>

    @Query("SELECT * FROM oportunidades WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Long): OportunidadEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(oportunidad: OportunidadEntity): Long

    @Update
    suspend fun actualizar(oportunidad: OportunidadEntity)

    @Query("DELETE FROM oportunidades WHERE id = :id")
    suspend fun eliminarPorId(id: Long)
}
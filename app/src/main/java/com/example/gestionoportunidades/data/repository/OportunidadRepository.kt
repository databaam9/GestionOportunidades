package com.example.gestionoportunidades.data.repository

import com.example.gestionoportunidades.data.local.OportunidadDao
import com.example.gestionoportunidades.data.local.OportunidadEntity
import kotlinx.coroutines.flow.Flow

class OportunidadRepository(private val dao: OportunidadDao) {
    fun obtenerTodas(): Flow<List<OportunidadEntity>> = dao.obtenerTodas()
    suspend fun obtenerPorId(id: Long) = dao.obtenerPorId(id)
    suspend fun guardar(oportunidad: OportunidadEntity) = dao.insertar(oportunidad)
    suspend fun actualizar(oportunidad: OportunidadEntity) = dao.actualizar(oportunidad)
}
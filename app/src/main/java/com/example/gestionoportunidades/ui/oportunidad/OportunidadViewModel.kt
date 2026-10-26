package com.example.gestionoportunidades.ui.oportunidad

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionoportunidades.data.local.AppDatabase
import com.example.gestionoportunidades.data.local.OportunidadEntity
import com.example.gestionoportunidades.data.repository.OportunidadRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
class OportunidadViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = OportunidadRepository(AppDatabase.getInstance(app).oportunidadDao())
    var uiState by mutableStateOf(OportunidadUiState())
        private set
    val oportunidades: StateFlow<List<OportunidadEntity>> = repo.obtenerTodas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private var idActual: Long? = null
    fun onNombreChange(v: String) { uiState = uiState.copy(nombre = v, nombreError = null) }
    fun onTipoChange(v: String) { uiState = uiState.copy(tipo = v, tipoError = null) }
    fun onDescripcionChange(v: String) { uiState = uiState.copy(descripcion = v, descripcionError = null) }
    fun onFuenteChange(v: String) { uiState = uiState.copy(fuente = v, fuenteError = null) }
    fun onPrioridadChange(v: String) { uiState = uiState.copy(prioridad = v, prioridadError = null) }
    fun onPotencialChange(v: String) { uiState = uiState.copy(potencial = v, potencialError = null) }
    fun onObservacionesChange(v: String) { uiState = uiState.copy(observaciones = v) }
    fun onEvidenciaCapturada(uri: String) { uiState = uiState.copy(evidenciaUri = uri) }

    fun cargar(id: Long) {
        viewModelScope.launch {
            repo.obtenerPorId(id)?.let { op ->
                idActual = op.id
                uiState = uiState.copy(
                    nombre = op.nombre, tipo = op.tipo, descripcion = op.descripcion,
                    fuente = op.fuente, prioridad = op.prioridad, potencial = op.potencial,
                    estado = op.estado, observaciones = op.observaciones, evidenciaUri = op.evidenciaUri
                )
            }
        }
    }
    fun validarYGuardar() {
        val s = uiState
        val nombreErr = if (s.nombre.isBlank()) "Requerido" else null
        val tipoErr = if (s.tipo.isBlank()) "Requerido" else null
        val descErr = if (s.descripcion.isBlank()) "Requerido" else null
        val fuenteErr = if (s.fuente.isBlank()) "Requerido" else null
        val priorErr = if (s.prioridad.isBlank()) "Requerido" else null
        val potErr = if (s.potencial.isBlank()) "Requerido" else null

        if (listOf(nombreErr, tipoErr, descErr, fuenteErr, priorErr, potErr).any { it != null }) {
            uiState = s.copy(
                nombreError = nombreErr, tipoError = tipoErr, descripcionError = descErr,
                fuenteError = fuenteErr, prioridadError = priorErr, potencialError = potErr
            )
            return
        }
        viewModelScope.launch {
            uiState = uiState.copy(guardando = true)
            val entity = OportunidadEntity(
                id = idActual ?: 0L,
                nombre = s.nombre, tipo = s.tipo, descripcion = s.descripcion,
                fuente = s.fuente, prioridad = s.prioridad, potencial = s.potencial,
                estado = s.estado, observaciones = s.observaciones, evidenciaUri = s.evidenciaUri
            )
            if (idActual == null) repo.guardar(entity) else repo.actualizar(entity)
            uiState = uiState.copy(guardando = false, guardadoExitosamente = true)
        }
    }
    fun eliminarOportunidad(id: Long) {
        viewModelScope.launch {
            repo.eliminar(id)
        }
    }
}
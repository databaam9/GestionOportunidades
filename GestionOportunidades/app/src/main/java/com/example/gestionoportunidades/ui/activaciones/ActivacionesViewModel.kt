package com.example.gestionoportunidades.ui.activaciones

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionoportunidades.data.local.AppDatabase
import com.example.gestionoportunidades.data.local.OportunidadEntity
import com.example.gestionoportunidades.data.repository.OportunidadRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActivacionesViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = OportunidadRepository(AppDatabase.getInstance(app).oportunidadDao())

    val oportunidades: StateFlow<List<OportunidadEntity>> = repo.obtenerTodas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun registrarEvidencia(oportunidad: OportunidadEntity, uri: String) {
        viewModelScope.launch {
            val actualizada = oportunidad.copy(
                evidenciaUri = uri
            )
            repo.actualizar(actualizada)
        }
    }

    fun coordinarActivacion(
        oportunidad: OportunidadEntity,
        responsable: String,
        condiciones: String,
        participacionConfirmada: Boolean
    ) {
        viewModelScope.launch {
            val actualizada = oportunidad.copy(
                responsableActivacion = responsable,
                condicionesActivacion = condiciones,
                participacionConfirmada = participacionConfirmada
            )
            repo.actualizar(actualizada)
        }
    }

    fun registrarResultado(
        oportunidad: OportunidadEntity,
        resultado: String,
        proximasAcciones: String,
        observaciones: String
    ) {
        viewModelScope.launch {
            val actualizada = oportunidad.copy(
                resultadoActivacion = resultado,
                proximasAcciones = proximasAcciones,
                observaciones = observaciones
            )
            repo.actualizar(actualizada)
        }
    }
}
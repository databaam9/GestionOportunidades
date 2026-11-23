package com.example.gestionoportunidades.ui.ejecutivo
import android.util.Log
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionoportunidades.data.local.AppDatabase
import com.example.gestionoportunidades.data.local.OportunidadEntity
import com.example.gestionoportunidades.data.remote.RetrofitClient
import com.example.gestionoportunidades.data.repository.OportunidadRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EjecutivoViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = OportunidadRepository(AppDatabase.getInstance(app).oportunidadDao())

    val oportunidades: StateFlow<List<OportunidadEntity>> = repo.obtenerTodas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun importarEventosDeApi() {
        viewModelScope.launch {
            try {

                val eventosApi = RetrofitClient.apiService.getEventos()
                Log.d("API_DEBUG", "Eventos recibidos de la API: ${eventosApi.size}")

                eventosApi.forEach { evento ->
                    val nuevaOportunidad = OportunidadEntity(
                        id = 0L,
                        nombre = evento.nombre,
                        tipo = "Evento Externo",

                        descripcion = "Evento importado del catálogo de la API",
                        fuente = "API",
                        prioridad = "Media",
                        potencial = "Por evaluar",
                        estado = "Pendiente",
                        observaciones = "Importado desde API",
                        organizacion = evento.organizacion,
                        fechaEvento = evento.fecha,
                        ubicacion = evento.ubicacion
                    )

                    repo.guardar(nuevaOportunidad)
                }
                Log.d("API_DEBUG", "Datos guardados en Room exitosamente")
            } catch (e: Exception) {
                Log.e("API_DEBUG", "Error al importar: ${e.message}", e)
            }
        }
    }
}
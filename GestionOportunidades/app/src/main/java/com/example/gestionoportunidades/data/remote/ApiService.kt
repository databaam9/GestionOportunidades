package com.example.gestionoportunidades.data.remote

import retrofit2.http.GET

data class EventoApi(
    val id: Int,
    val nombre: String,
    val organizacion: String,
    val fecha: String,
    val ubicacion: String
)

interface ApiService {
    @GET("api/eventos")
    suspend fun getEventos(): List<EventoApi>
}
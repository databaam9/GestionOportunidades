package com.example.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// Modelo de datos
data class Evento(
    val id: Int,
    val nombre: String,
    val organizacion: String,
    val fecha: String,
    val ubicacion: String
)

@RestController
@RequestMapping("/api")
class EventoController {

    @GetMapping("/eventos")
    fun getEventos(): List<Evento> {
        return listOf(
            Evento(1, "Feria Tecnológica 2026", "TechCorp", "2026-05-15", "Centro de Convenciones, Ciudad de México"),
            Evento(2, "Congreso de Marketing", "MediaGroup", "2026-06-20", "Hotel Marriott, Monterrey"),
            Evento(3, "Expo Comercial Norte", "Cámara de Comercio", "2026-07-10", "Expo Guadalajara")
        )
    }
}
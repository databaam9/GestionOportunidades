package com.example.gestionoportunidades.ui.oportunidad

data class OportunidadUiState(
    val nombre: String = "",
    val tipo: String = "",
    val descripcion: String = "",
    val fuente: String = "",
    val prioridad: String = "",
    val potencial: String = "",
    val estado: String = "Evento identificado",
    val observaciones: String = "",

    val nombreError: String? = null,
    val tipoError: String? = null,
    val descripcionError: String? = null,
    val prioridadError: String? = null,
    val fuenteError: String? = null,
    val potencialError: String? = null,

    val guardando: Boolean = false,
    val guardadoExitosamente: Boolean = false,
    val evidenciaUri: String? = null
)
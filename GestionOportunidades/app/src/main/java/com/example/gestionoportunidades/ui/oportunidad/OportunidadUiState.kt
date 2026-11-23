package com.example.gestionoportunidades.ui.oportunidad

data class OportunidadUiState(
    val nombre: String = "",
    val tipo: String = "",
    val descripcion: String = "",
    val fuente: String = "",
    val prioridad: String = "",
    val potencial: String = "",
    val estado: String = "Pendiente",
    val observaciones: String = "",
    val organizacion: String = "",
    val contacto: String = "",
    val fechaEvento: String = "",
    val gestionRealizada: Boolean = false,
    val fechaGestion: String = "",
    val ubicacion: String = "",

    val nombreError: String? = null,
    val tipoError: String? = null,
    val descripcionError: String? = null,
    val prioridadError: String? = null,
    val fuenteError: String? = null,
    val potencialError: String? = null,
    val organizacionError: String? = null,
    val contactoError: String? = null,
    val fechaEventoError: String? = null,

    val guardando: Boolean = false,
    val guardadoExitosamente: Boolean = false,
    val evidenciaUri: String? = null
)
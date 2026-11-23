package com.example.gestionoportunidades.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "oportunidades")
data class OportunidadEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val nombre: String,
    val tipo: String,
    val descripcion: String,
    val fuente: String,
    val prioridad: String,
    val potencial: String,
    val estado: String = "Pendiente",
    val observaciones: String,
    val evidenciaUri: String? = null,
    val organizacion: String = "",
    val contacto: String = "",
    val fechaEvento: String = "",
    val gestionRealizada: Boolean = false,
    val fechaGestion: String = "",
    val responsableActivacion: String = "",
    val condicionesActivacion: String = "",
    val participacionConfirmada: Boolean = false,
    val resultadoActivacion: String = "",
    val proximasAcciones: String = "",
    val ubicacion: String = ""
)
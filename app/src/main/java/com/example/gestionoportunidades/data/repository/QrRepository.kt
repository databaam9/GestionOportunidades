package com.example.gestionoportunidades.data.repository

import com.example.gestionoportunidades.data.local.QrResult

class QrRepository {
    fun processQrContent(content: String): QrResult {
        // Aquí podrías guardar o procesar el QR en una BD o API
        return QrResult(content)
    }
}
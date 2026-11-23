package com.example.gestionoportunidades.data.local

data class Credential(
    val username: String,
    val password: String,
    val role: UserRole
) {
    companion object {
        val Ejecutivo = Credential("ejecutivo", "123", UserRole.EJECUTIVO)
        val Supervisor = Credential("supervisor", "123", UserRole.SUPERVISOR)
        val Activaciones = Credential("activaciones", "123", UserRole.ACTIVACIONES)

        val AllCredentials = listOf(Ejecutivo, Supervisor, Activaciones)
    }
}
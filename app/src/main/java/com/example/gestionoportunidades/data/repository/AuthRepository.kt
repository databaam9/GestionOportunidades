package com.example.gestionoportunidades.data.repository
import com.example.gestionoportunidades.data.local.Credential
class AuthRepository(
    private val validCredential: Credential = Credential.Admin
) {
    fun login(username: String, password: String): Boolean {
        return username == validCredential.username && password == validCredential.password
    }
}
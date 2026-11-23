package com.example.gestionoportunidades.data.repository
import com.example.gestionoportunidades.data.local.Credential
import com.example.gestionoportunidades.data.local.UserRole

class AuthRepository {
    fun login(username: String, password: String): UserRole? {
        val credential = Credential.AllCredentials.find {
            it.username == username && it.password == password
        }
        return credential?.role
    }
}
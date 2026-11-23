package com.example.gestionoportunidades.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestionoportunidades.data.local.UserRole
import com.example.gestionoportunidades.data.repository.AuthRepository

class LoginViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(value: String) {
        uiState = uiState.copy(username = value, error = null)
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value, error = null)
    }


    fun submit(onSuccess: (UserRole) -> Unit) {
        uiState = uiState.copy(isLoading = true, error = null)
        val role = repo.login(uiState.username.trim(), uiState.password)
        uiState = uiState.copy(isLoading = false)

        if (role != null) {
            onSuccess(role)
        } else {
            uiState = uiState.copy(error = "Credenciales inválidas")
        }
    }
}
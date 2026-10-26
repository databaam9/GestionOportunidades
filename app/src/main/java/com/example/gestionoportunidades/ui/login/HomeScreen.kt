package com.example.gestionoportunidades.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gestionoportunidades.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    vm: LoginViewModel = viewModel()
) {
    val state = vm.uiState
    var showPass by remember { mutableStateOf(false) }

    val colorScheme = darkColorScheme(
        primary = Color(0xFF98222E),
        onPrimary = Color.White,
        onSurface = Color(0xFF333333)
    )

    MaterialTheme(colorScheme = colorScheme) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text("Gestion De Oportunidades", color = MaterialTheme.colorScheme.onPrimary)
                })
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color(0xFFF0F0F0)),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Bienvenido",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(66.dp))

                Image(
                    painter = painterResource(id = R.drawable.`is`),
                    contentDescription = "Logo App",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Fit
                )

                OutlinedTextField(
                    value = state.username,
                    onValueChange = vm::onUsernameChange,
                    label = { Text("Usuario") },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(0.95f)
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onPasswordChange,
                    label = { Text("Contraseña") },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPass = !showPass }) {
                            Icon(
                                imageVector = if (showPass) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.95f)
                )

                AnimatedVisibility(
                    visible = state.error != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = state.error ?: "",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(66.dp))

                Button(
                    onClick = {
                        vm.submit { user ->
                            navController.navigate("DrawerMenu/$user") {
                                popUpTo("login") { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(if (state.isLoading) "Validando" else "Iniciar sesion")
                }
            }
        }
    }
}

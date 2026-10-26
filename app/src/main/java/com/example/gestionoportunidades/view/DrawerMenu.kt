package com.example.gestionoportunidades.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DrawerMenu(
    username: String,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Bienvenido,\n$username",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }

        LazyColumn(modifier = Modifier.weight(1f)) {

            item {
                NavigationDrawerItem(
                    label = { Text("Gestión de Oportunidades") },
                    selected = false,
                    onClick = { navController.navigate("listaOportunidades") },
                    icon = {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Oportunidades"
                        )
                    }
                )
            }

            item {
                NavigationDrawerItem(
                    label = { Text("Cerrar Sesión") },
                    selected = false,
                    onClick = {
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar Sesión"
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerMenuPreview() {
    val navController = rememberNavController()
    DrawerMenu(username = "Usuario Prueba", navController = navController)
}

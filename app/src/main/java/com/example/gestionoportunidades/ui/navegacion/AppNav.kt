package com.example.gestionoportunidades.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gestionoportunidades.ui.oportunidad.ListaOportunidadesScreen
import com.example.gestionoportunidades.ui.oportunidad.OportunidadFormScreen
import com.example.gestionoportunidades.view.DrawerMenu
import com.example.gestionoportunidades.ui.theme.HomeScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            HomeScreen(navController = navController)
        }

        composable(
            route = "DrawerMenu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            DrawerMenu(username = username, navController = navController)
        }

        composable("listaOportunidades") {
            ListaOportunidadesScreen(
                onNueva = { navController.navigate("oportunidadForm/-1") },
                onEditar = { id -> navController.navigate("oportunidadForm/$id") }
            )
        }

        composable(
            route = "oportunidadForm/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: -1L
            OportunidadFormScreen(
                id = if (id == -1L) null else id,
                onGuardado = { navController.popBackStack() }
            )
        }
    }
}
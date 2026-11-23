package com.example.gestionoportunidades.ui.navegacion

import DrawerMenu
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gestionoportunidades.data.local.UserRole
import com.example.gestionoportunidades.ui.activaciones.ActivacionesHomeScreen
import com.example.gestionoportunidades.ui.oportunidad.ListaOportunidadesScreen
import com.example.gestionoportunidades.ui.oportunidad.OportunidadFormScreen
import com.example.gestionoportunidades.ui.theme.HomeScreen


@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            HomeScreen(navController = navController)
        }


        composable(
            route = "DrawerMenu/{username}/{role}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("role") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            val roleName = backStackEntry.arguments?.getString("role").orEmpty()
            val role = UserRole.valueOf(roleName)

            DrawerMenu(
                username = username,
                role = role,
                navController = navController
            )
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


        composable("activaciones") {
            ActivacionesHomeScreen()
        }
    }
}
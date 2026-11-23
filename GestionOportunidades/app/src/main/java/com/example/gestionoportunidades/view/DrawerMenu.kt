import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestionoportunidades.data.local.UserRole
import com.example.gestionoportunidades.ui.activaciones.ActivacionesHomeScreen
import com.example.gestionoportunidades.ui.ejecutivo.EjecutivoHomeScreen
import com.example.gestionoportunidades.ui.oportunidad.ListaOportunidadesScreen
import com.example.gestionoportunidades.ui.oportunidad.OportunidadFormScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenu(
    username: String,
    role: UserRole,
    navController: NavController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Estado para saber qué pantalla mostrar internamente
    var currentScreen by remember { mutableStateOf("home") }
    var editingId by remember { mutableStateOf<Long?>(null) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Perfil: $username",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Rol: ${role.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp))


                when (role) {
                    UserRole.EJECUTIVO -> {
                        NavigationDrawerItem(
                            label = { Text("Mis Oportunidades") },
                            icon = { Icon(Icons.Default.List, contentDescription = null) },
                            selected = currentScreen == "lista",
                            onClick = {
                                currentScreen = "lista"
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                    UserRole.SUPERVISOR -> {
                        NavigationDrawerItem(
                            label = { Text("Panel de Control") },
                            icon = { Icon(Icons.Default.List, contentDescription = null) },
                            selected = currentScreen == "lista",
                            onClick = {
                                currentScreen = "lista"
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                    UserRole.ACTIVACIONES -> {
                        NavigationDrawerItem(
                            label = { Text("Activaciones Pendientes") },
                            icon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
                            selected = currentScreen == "activaciones",
                            onClick = {
                                currentScreen = "activaciones"
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                }

                Spacer(Modifier.weight(1f))
                Divider()
                NavigationDrawerItem(
                    label = { Text("Cerrar Sesión") },
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
                    selected = false,
                    onClick = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Gestión Oportunidades") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            Box(Modifier.padding(padding)) {

                when (role) {
                    UserRole.EJECUTIVO -> {
                        when (currentScreen) {
                            "lista", "home" -> EjecutivoHomeScreen(
                                onNuevaOportunidad = {
                                    editingId = null
                                    currentScreen = "form"
                                },
                                onEditarOportunidad = { id ->
                                    editingId = id
                                    currentScreen = "form"
                                }
                            )
                            "form" -> OportunidadFormScreen(
                                id = editingId,
                                onGuardado = { currentScreen = "lista" }
                            )
                        }
                    }

                    UserRole.SUPERVISOR -> {
                        when (currentScreen) {
                            "lista", "home" -> ListaOportunidadesScreen(
                                onNueva = {
                                    editingId = null
                                    currentScreen = "form"
                                },
                                onEditar = { id ->
                                    editingId = id
                                    currentScreen = "form"
                                }
                            )
                            "form" -> OportunidadFormScreen(
                                id = editingId,
                                onGuardado = { currentScreen = "lista" }
                            )
                        }
                    }

                    UserRole.ACTIVACIONES -> {
                        ActivacionesHomeScreen()
                    }
                }
            }
        }
    }
}
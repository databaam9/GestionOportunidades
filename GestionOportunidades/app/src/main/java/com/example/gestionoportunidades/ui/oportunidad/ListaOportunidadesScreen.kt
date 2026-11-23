package com.example.gestionoportunidades.ui.oportunidad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaOportunidadesScreen(
    onNueva: () -> Unit,
    onEditar: (Long) -> Unit,
    vm: OportunidadViewModel = viewModel()
) {
    val lista by vm.oportunidades.collectAsState()

    var textoBusqueda by remember { mutableStateOf("") }
    val listaFiltrada = lista.filter { op ->
        textoBusqueda.isBlank() ||
            op.nombre.contains(textoBusqueda, ignoreCase = true) ||
            op.organizacion.contains(textoBusqueda, ignoreCase = true) ||
            op.estado.contains(textoBusqueda, ignoreCase = true)
    }

    var oportunidadAEliminar by remember { mutableStateOf<Long?>(null) }

    if (oportunidadAEliminar != null) {
        AlertDialog(
            onDismissRequest = { oportunidadAEliminar = null },
            title = { Text("Eliminar Oportunidad") },
            text = { Text("¿Estás seguro de que deseas eliminar esta oportunidad? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        oportunidadAEliminar?.let { vm.eliminarOportunidad(it) }
                        oportunidadAEliminar = null
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { oportunidadAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Oportunidades") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onNueva) { Text("+") }
        }
    ) { pad ->
        Column(Modifier.padding(pad).fillMaxSize()) {
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                label = { Text("Buscar por nombre, organización o estado") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            if (listaFiltrada.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay oportunidades registradas")
                }
            } else {
                LazyColumn(
                    Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listaFiltrada) { op ->
                        Card(
                            Modifier.fillMaxWidth().clickable { onEditar(op.id) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Nombre: ${op.nombre}", style = MaterialTheme.typography.titleMedium)
                                    Text("Organización: ${op.organizacion}")
                                    Text("Tipo: ${op.tipo}")
                                    Text("Prioridad: ${op.prioridad}")
                                    Text("Estado: ${op.estado}")
                                }

                                IconButton(
                                    onClick = { oportunidadAEliminar = op.id }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
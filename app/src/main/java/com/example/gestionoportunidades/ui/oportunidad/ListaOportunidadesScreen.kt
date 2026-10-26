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
        if (lista.isEmpty()) {
            Box(Modifier.padding(pad).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay oportunidades registradas")
            }
        } else {
            LazyColumn(
                Modifier.padding(pad).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(lista) { op ->
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
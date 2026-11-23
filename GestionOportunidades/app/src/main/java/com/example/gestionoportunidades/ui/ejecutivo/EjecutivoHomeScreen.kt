package com.example.gestionoportunidades.ui.ejecutivo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EjecutivoHomeScreen(
    onNuevaOportunidad: () -> Unit,
    onEditarOportunidad: (Long) -> Unit,
    vm: EjecutivoViewModel = viewModel()
) {
    val lista by vm.oportunidades.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Panel Ejecutivo") }) },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {

                ExtendedFloatingActionButton(
                    text = { Text("Importar API") },
                    icon = { Icon(Icons.Default.CloudDownload, contentDescription = null) },
                    onClick = { vm.importarEventosDeApi() },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FloatingActionButton(onClick = onNuevaOportunidad) {
                    Icon(Icons.Default.Add, contentDescription = "Nueva")
                }
            }
        }
    ) { pad ->
        if (lista.isEmpty()) {
            Box(Modifier.padding(pad).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay oportunidades. Usa el botón de la nube para importar.")
            }
        } else {
            LazyColumn(
                Modifier.padding(pad).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(lista) { op ->
                    Card(
                        Modifier.fillMaxWidth().clickable { onEditarOportunidad(op.id) }
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(op.nombre, style = MaterialTheme.typography.titleMedium)
                            Text("Organización: ${op.organizacion}", style = MaterialTheme.typography.bodySmall)
                            Text(op.descripcion, style = MaterialTheme.typography.bodySmall)
                            Text("Estado: ${op.estado}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
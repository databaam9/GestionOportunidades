package com.example.gestionoportunidades.ui.oportunidad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun ListaOportunidadesScreen(
    onNueva: () -> Unit,
    onEditar: (Long) -> Unit,
    vm: OportunidadViewModel = viewModel()
) {
    val lista by vm.oportunidades.collectAsState()

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
            LazyColumn(Modifier.padding(pad).fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(lista) { op ->
                    Card(Modifier.fillMaxWidth().clickable { onEditar(op.id) }) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Nombre: ${op.nombre}", style = MaterialTheme.typography.titleMedium)
                            Text("Tipo: ${op.tipo}")
                            Text("Prioridad: ${op.prioridad}")
                            Text("Estado: ${op.estado}")
                        }
                    }
                }
            }
        }
    }
}
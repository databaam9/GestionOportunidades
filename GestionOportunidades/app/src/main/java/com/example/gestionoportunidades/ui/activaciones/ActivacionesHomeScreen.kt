package com.example.gestionoportunidades.ui.activaciones

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionoportunidades.data.local.OportunidadEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivacionesHomeScreen(
    vm: ActivacionesViewModel = viewModel()
) {
    val lista by vm.oportunidades.collectAsState()

    var oportunidadAAdjuntar by remember { mutableStateOf<OportunidadEntity?>(null) }
    var oportunidadACoordinar by remember { mutableStateOf<OportunidadEntity?>(null) }
    var oportunidadAResultado by remember { mutableStateOf<OportunidadEntity?>(null) }

    if (oportunidadAAdjuntar != null) {
        AlertDialog(
            onDismissRequest = { oportunidadAAdjuntar = null },
            title = { Text("Adjuntar Evidencia") },
            text = { Text("¿Simular la captura de foto para: ${oportunidadAAdjuntar?.nombre}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        oportunidadAAdjuntar?.let { op ->
                            vm.registrarEvidencia(op, "uri_evidencia_${op.id}.jpg")
                        }
                        oportunidadAAdjuntar = null // Cerramos el diálogo
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { oportunidadAAdjuntar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (oportunidadACoordinar != null) {
        val op = oportunidadACoordinar!!
        var responsable by remember { mutableStateOf(op.responsableActivacion) }
        var condiciones by remember { mutableStateOf(op.condicionesActivacion) }
        var confirmada by remember { mutableStateOf(op.participacionConfirmada) }

        AlertDialog(
            onDismissRequest = { oportunidadACoordinar = null },
            title = { Text("Coordinar Activación") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = responsable,
                        onValueChange = { responsable = it },
                        label = { Text("Responsable") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = condiciones,
                        onValueChange = { condiciones = it },
                        label = { Text("Condiciones") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = confirmada, onCheckedChange = { confirmada = it })
                        Text("Confirmar participación")
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        vm.coordinarActivacion(op, responsable, condiciones, confirmada)
                        oportunidadACoordinar = null
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { oportunidadACoordinar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (oportunidadAResultado != null) {
        val op = oportunidadAResultado!!
        var resultado by remember { mutableStateOf(op.resultadoActivacion) }
        var proximasAcciones by remember { mutableStateOf(op.proximasAcciones) }
        var observaciones by remember { mutableStateOf(op.observaciones) }

        AlertDialog(
            onDismissRequest = { oportunidadAResultado = null },
            title = { Text("Registro de Resultados") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = resultado,
                        onValueChange = { resultado = it },
                        label = { Text("Resultado de la activación") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = observaciones,
                        onValueChange = { observaciones = it },
                        label = { Text("Observaciones") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = proximasAcciones,
                        onValueChange = { proximasAcciones = it },
                        label = { Text("Próximas acciones") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        vm.registrarResultado(op, resultado, proximasAcciones, observaciones)
                        oportunidadAResultado = null
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { oportunidadAResultado = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Activaciones") }) }
    ) { pad ->

        val pendientes = lista.filter { it.estado == "Confirmada" }

        if (pendientes.isEmpty()) {
            Box(Modifier.padding(pad).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay activaciones pendientes")
            }
        } else {
            LazyColumn(
                Modifier.padding(pad).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pendientes) { op ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            Text(op.nombre, style = MaterialTheme.typography.titleLarge)
                            Text("Organización: ${op.organizacion}")
                            Text("Estado: ${op.estado}")
                            Text("Lugar: ${op.ubicacion}")
                            if (op.responsableActivacion.isNotBlank()) {
                                Text("Responsable: ${op.responsableActivacion}")
                            }
                            if (op.resultadoActivacion.isNotBlank()) {
                                Text("Resultado: ${op.resultadoActivacion}")
                            }

                            Spacer(Modifier.height(8.dp))

                            Button(
                                onClick = { oportunidadACoordinar = op },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(if (op.participacionConfirmada) "Participación confirmada" else "Coordinar Activación")
                            }

                            Spacer(Modifier.height(8.dp))

                            Button(
                                onClick = { oportunidadAAdjuntar = op },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = op.evidenciaUri == null
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text(if (op.evidenciaUri == null) "Adjuntar Evidencia" else "Evidencia adjuntada")
                            }

                            Spacer(Modifier.height(8.dp))

                            Button(
                                onClick = { oportunidadAResultado = op },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(if (op.resultadoActivacion.isBlank()) "Registrar Resultado" else "Editar Resultado")
                            }
                        }
                    }
                }
            }
        }
    }
}

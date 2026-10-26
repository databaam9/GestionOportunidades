package com.example.gestionoportunidades.ui.oportunidad

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionoportunidades.ui.theme.utils.CameraPermissionHelper
import com.example.gestionoportunidades.ui.theme.utils.QrScanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OportunidadFormScreen(
    id: Long?,
    onGuardado: () -> Unit,
    vm: OportunidadViewModel = viewModel()
) {
    val state = vm.uiState
    val context = LocalContext.current


    var mostrarQr by remember { mutableStateOf(false) }
    var tienePermiso by remember {
        mutableStateOf(CameraPermissionHelper.hasCameraPermission(context))
    }


    val permisoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        tienePermiso = granted
        if (granted) mostrarQr = true
    }


    LaunchedEffect(id) { id?.let { vm.cargar(it) } }
    LaunchedEffect(state.guardadoExitosamente) { if (state.guardadoExitosamente) onGuardado() }

    Scaffold(
        topBar = { TopAppBar(title = { Text(if (id == null) "Nueva Oportunidad" else "Editar Oportunidad") }) }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = state.nombre,
                onValueChange = vm::onNombreChange,
                label = { Text("Nombre") },
                isError = state.nombreError != null,
                supportingText = { Text(state.nombreError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.tipo,
                onValueChange = vm::onTipoChange,
                label = { Text("Tipo") },
                isError = state.tipoError != null,
                supportingText = { Text(state.tipoError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.descripcion,
                onValueChange = vm::onDescripcionChange,
                label = { Text("Descripción") },
                isError = state.descripcionError != null,
                supportingText = { Text(state.descripcionError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.fuente,
                onValueChange = vm::onFuenteChange,
                label = { Text("Código QR") },
                isError = state.fuenteError != null,
                supportingText = { Text(state.fuenteError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.prioridad,
                onValueChange = vm::onPrioridadChange,
                label = { Text("Prioridad") },
                isError = state.prioridadError != null,
                supportingText = { Text(state.prioridadError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.potencial,
                onValueChange = vm::onPotencialChange,
                label = { Text("Potencial Comercial") },
                isError = state.potencialError != null,
                supportingText = { Text(state.potencialError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.observaciones,
                onValueChange = vm::onObservacionesChange,
                label = { Text("Observaciones") },
                modifier = Modifier.fillMaxWidth()
            )

            if (!mostrarQr) {
                Button(
                    onClick = {
                        if (tienePermiso) {
                            mostrarQr = true
                        } else {
                            permisoLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Escanear Código QR ")
                }
            } else {

                Text("Enfoca el código QR", style = MaterialTheme.typography.bodyMedium)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    // Llamamos al componente QrScanner
                    QrScanner(
                        onQrCodeScanned = { codigoEscaneado ->

                            vm.onFuenteChange(codigoEscaneado)
                            mostrarQr = false
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                TextButton(
                    onClick = { mostrarQr = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar Escaneo")
                }
            }

            Button(
                onClick = vm::validarYGuardar,
                enabled = !state.guardando,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.guardando) "Guardando..." else "Guardar")
            }
        }
    }
}
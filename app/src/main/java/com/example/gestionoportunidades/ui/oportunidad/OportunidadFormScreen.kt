package com.example.gestionoportunidades.ui.oportunidad

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestionoportunidades.ui.utils.CameraPermissionHelper
import com.example.gestionoportunidades.ui.utils.QrScanner

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
    var evidenciaBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val permisoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        tienePermiso = granted
        if (granted) mostrarQr = true
    }

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            vm.onEvidenciaCapturada(uri.toString())
            context.contentResolver.openInputStream(uri)?.use { input ->
                evidenciaBitmap = BitmapFactory.decodeStream(input)
            }
        }
    }

    LaunchedEffect(id) { id?.let { vm.cargar(it) } }
    LaunchedEffect(state.guardadoExitosamente) { if (state.guardadoExitosamente) onGuardado() }
    LaunchedEffect(state.evidenciaUri) {
        val uriString = state.evidenciaUri
        if (uriString != null && evidenciaBitmap == null) {
            val uri = Uri.parse(uriString)
            context.contentResolver.openInputStream(uri)?.use { input ->
                evidenciaBitmap = BitmapFactory.decodeStream(input)
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(if (id == null) "Nueva Oportunidad" else "Editar Oportunidad") }) }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = state.nombre,
                onValueChange = vm::onNombreChange,
                label = { Text("Nombre") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                isError = state.nombreError != null,
                supportingText = { Text(state.nombreError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.tipo,
                onValueChange = vm::onTipoChange,
                label = { Text("Tipo") },
                leadingIcon = { Icon(Icons.Default.Category, contentDescription = null) },
                isError = state.tipoError != null,
                supportingText = { Text(state.tipoError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.descripcion,
                onValueChange = vm::onDescripcionChange,
                label = { Text("Descripción") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                isError = state.descripcionError != null,
                supportingText = { Text(state.descripcionError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.fuente,
                onValueChange = vm::onFuenteChange,
                label = { Text("Código QR") },
                leadingIcon = { Icon(Icons.Default.QrCode, contentDescription = null) },
                isError = state.fuenteError != null,
                supportingText = { Text(state.fuenteError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.prioridad,
                onValueChange = vm::onPrioridadChange,
                label = { Text("Prioridad") },
                leadingIcon = { Icon(Icons.Default.PriorityHigh, contentDescription = null) },
                isError = state.prioridadError != null,
                supportingText = { Text(state.prioridadError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.potencial,
                onValueChange = vm::onPotencialChange,
                label = { Text("Potencial Comercial") },
                leadingIcon = { Icon(Icons.Default.TrendingUp, contentDescription = null) },
                isError = state.potencialError != null,
                supportingText = { Text(state.potencialError ?: "") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.observaciones,
                onValueChange = vm::onObservacionesChange,
                label = { Text("Observaciones") },
                leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            AnimatedVisibility(
                visible = !mostrarQr,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
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
                    Text("Escanear Código QR")
                }
            }

            AnimatedVisibility(
                visible = mostrarQr,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Text("Enfoca el código QR", style = MaterialTheme.typography.bodyMedium)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
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
            }

            Button(
                onClick = {
                    galeriaLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Icon(Icons.Default.AddAPhoto, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Adjuntar Evidencia Fotográfica")
            }

            AnimatedVisibility(
                visible = evidenciaBitmap != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                evidenciaBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Evidencia fotográfica",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
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

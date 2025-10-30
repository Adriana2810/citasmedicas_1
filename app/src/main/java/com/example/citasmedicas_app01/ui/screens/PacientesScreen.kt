package com.example.citasmedicas_app01.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.citasmedicas_app01.data.entity.Paciente
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PacientesScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddPaciente: () -> Unit,
    pacientes: List<Paciente>,
    onUpdatePaciente: (Paciente) -> Unit,
    onDeletePaciente: (Paciente) -> Unit,
    onNavigateToEditPaciente: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pacientes") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddPaciente
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Paciente")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (pacientes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No hay pacientes registrados",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Toca el botón + para agregar un nuevo paciente",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(pacientes) { paciente ->
                        PacienteCard(
                            paciente = paciente,
                            onUpdate = onUpdatePaciente,
                            onDelete = onDeletePaciente,
                            onEditNavigate = { onNavigateToEditPaciente(paciente.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PacienteCard(
    paciente: Paciente,
    onUpdate: (Paciente) -> Unit,
    onDelete: (Paciente) -> Unit,
    onEditNavigate: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var showEdit by remember { mutableStateOf(false) }
    var showDelete by remember { mutableStateOf(false) }
    var editedNombre by remember { mutableStateOf(paciente.nombre) }
    var editedTelefono by remember { mutableStateOf(paciente.telefono) }
    var editedEmail by remember { mutableStateOf(paciente.email ?: "") }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = paciente.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Teléfono: ${paciente.telefono}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            paciente.email?.let { email ->
                Text(
                    text = "Email: $email",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Text(
                text = "Registrado: ${dateFormat.format(Date(paciente.fechaCreacion))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(onClick = onEditNavigate) { Text("Editar") }

                TextButton(
                    onClick = { showDelete = true },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("Eliminar") }
            }
        }
    }

    // Edición se mueve a pantalla dedicada

    if (showDelete) {
        AlertDialog(
            onDismissRequest = { showDelete = false },
            title = { Text("Eliminar paciente") },
            text = { Text("¿Seguro que deseas eliminar a ${paciente.nombre}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(paciente)
                        showDelete = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { showDelete = false }) { Text("Cancelar") }
            }
        )
    }
}


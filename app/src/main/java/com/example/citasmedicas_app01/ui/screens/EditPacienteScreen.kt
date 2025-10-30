package com.example.citasmedicas_app01.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.citasmedicas_app01.data.entity.Paciente

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPacienteScreen(
    paciente: Paciente,
    onNavigateBack: () -> Unit,
    onUpdatePaciente: (Paciente) -> Unit,
    onDeletePaciente: (Paciente) -> Unit
) {
    var nombre by remember { mutableStateOf(paciente.nombre) }
    var telefono by remember { mutableStateOf(paciente.telefono) }
    var email by remember { mutableStateOf(paciente.email ?: "") }
    var showDelete by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Paciente") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Actualizar datos del paciente",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && telefono.isNotBlank()) {
                        val actualizado = paciente.copy(
                            nombre = nombre.trim(),
                            telefono = telefono.trim(),
                            email = email.trim().ifBlank { null }
                        )
                        onUpdatePaciente(actualizado)
                        onNavigateBack()
                    }
                },
                enabled = nombre.isNotBlank() && telefono.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Guardar cambios") }

            TextButton(
                onClick = { showDelete = true },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Eliminar paciente") }
        }
    }

    if (showDelete) {
        AlertDialog(
            onDismissRequest = { showDelete = false },
            title = { Text("Eliminar paciente") },
            text = { Text("¿Seguro que deseas eliminar a ${paciente.nombre}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeletePaciente(paciente)
                        showDelete = false
                        onNavigateBack()
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



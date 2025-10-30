package com.example.citasmedicas_app01.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.citasmedicas_app01.data.entity.Doctor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDoctorScreen(
    doctor: Doctor,
    onNavigateBack: () -> Unit,
    onUpdateDoctor: (Doctor) -> Unit,
    onDeleteDoctor: (Doctor) -> Unit
) {
    var nombre by remember { mutableStateOf(doctor.nombre) }
    var especialidad by remember { mutableStateOf(doctor.especialidad) }
    var telefono by remember { mutableStateOf(doctor.telefono) }
    var email by remember { mutableStateOf(doctor.email ?: "") }
    var inicio by remember { mutableStateOf(doctor.horarioInicio) }
    var fin by remember { mutableStateOf(doctor.horarioFin) }
    var showDelete by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Doctor") },
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
                text = "Actualizar datos del doctor",
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
                value = especialidad,
                onValueChange = { especialidad = it },
                label = { Text("Especialidad *") },
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

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = inicio,
                    onValueChange = { inicio = it },
                    label = { Text("Horario Inicio") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = fin,
                    onValueChange = { fin = it },
                    label = { Text("Horario Fin") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && especialidad.isNotBlank() && telefono.isNotBlank()) {
                        val actualizado = doctor.copy(
                            nombre = nombre.trim(),
                            especialidad = especialidad.trim(),
                            telefono = telefono.trim(),
                            email = email.trim().ifBlank { null },
                            horarioInicio = inicio.trim().ifBlank { "09:00" },
                            horarioFin = fin.trim().ifBlank { "17:00" }
                        )
                        onUpdateDoctor(actualizado)
                        onNavigateBack()
                    }
                },
                enabled = nombre.isNotBlank() && especialidad.isNotBlank() && telefono.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Guardar cambios") }

            TextButton(
                onClick = { showDelete = true },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Eliminar doctor") }
        }
    }

    if (showDelete) {
        AlertDialog(
            onDismissRequest = { showDelete = false },
            title = { Text("Eliminar doctor") },
            text = { Text("¿Seguro que deseas eliminar a ${doctor.nombre}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteDoctor(doctor)
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



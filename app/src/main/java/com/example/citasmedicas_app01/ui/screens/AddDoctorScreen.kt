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
fun AddDoctorScreen(
    onNavigateBack: () -> Unit,
    onSaveDoctor: (Doctor) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var horarioInicio by remember { mutableStateOf("09:00") }
    var horarioFin by remember { mutableStateOf("17:00") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Doctor") },
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
                text = "Registrar Nuevo Doctor",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Ej: Dr. Juan Pérez") }
            )
            
            OutlinedTextField(
                value = especialidad,
                onValueChange = { especialidad = it },
                label = { Text("Especialidad *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Ej: Cardiología, Pediatría, etc.") }
            )
            
            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Ej: 555-1234") }
            )
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Ej: doctor@clinica.com") }
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = horarioInicio,
                    onValueChange = { horarioInicio = it },
                    label = { Text("Horario Inicio") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = { Text("09:00") }
                )
                
                OutlinedTextField(
                    value = horarioFin,
                    onValueChange = { horarioFin = it },
                    label = { Text("Horario Fin") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = { Text("17:00") }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    if (nombre.isNotBlank() && especialidad.isNotBlank() && telefono.isNotBlank()) {
                        val doctor = Doctor(
                            nombre = nombre.trim(),
                            especialidad = especialidad.trim(),
                            telefono = telefono.trim(),
                            email = email.ifBlank { null },
                            horarioInicio = horarioInicio.ifBlank { "09:00" },
                            horarioFin = horarioFin.ifBlank { "17:00" }
                        )
                        onSaveDoctor(doctor)
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nombre.isNotBlank() && especialidad.isNotBlank() && telefono.isNotBlank()
            ) {
                Text("Guardar Doctor")
            }
        }
    }
}


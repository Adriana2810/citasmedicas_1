package com.example.citasmedicas_app01.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.citasmedicas_app01.data.entity.Doctor
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctoresScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddDoctor: () -> Unit,
    doctores: List<Doctor>,
    onUpdateDoctor: (Doctor) -> Unit,
    onDeleteDoctor: (Doctor) -> Unit,
    onNavigateToEditDoctor: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Doctores") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddDoctor
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Doctor")
            }
        }
    ) { paddingValues ->
        if (doctores.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "No hay doctores registrados",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Toca el botón + para agregar el primer doctor",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(doctores) { doctor ->
                    DoctorCard(
                        doctor = doctor,
                        onUpdate = onUpdateDoctor,
                        onDelete = onDeleteDoctor,
                        onEditNavigate = { onNavigateToEditDoctor(doctor.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun DoctorCard(
    doctor: Doctor,
    onUpdate: (Doctor) -> Unit,
    onDelete: (Doctor) -> Unit,
    onEditNavigate: () -> Unit
) {
    var showEdit by remember { mutableStateOf(false) }
    var showDelete by remember { mutableStateOf(false) }
    var editedNombre by remember { mutableStateOf(doctor.nombre) }
    var editedEspecialidad by remember { mutableStateOf(doctor.especialidad) }
    var editedTelefono by remember { mutableStateOf(doctor.telefono) }
    var editedEmail by remember { mutableStateOf(doctor.email ?: "") }
    var editedInicio by remember { mutableStateOf(doctor.horarioInicio) }
    var editedFin by remember { mutableStateOf(doctor.horarioFin) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = doctor.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = doctor.especialidad,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "📞 ${doctor.telefono}",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Text(
                    text = "🕐 ${doctor.horarioInicio} - ${doctor.horarioFin}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            if (!doctor.email.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "✉️ ${doctor.email}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

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
            title = { Text("Eliminar doctor") },
            text = { Text("¿Seguro que deseas eliminar a ${doctor.nombre}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(doctor)
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


package com.example.citasmedicas_app01.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.citasmedicas_app01.data.entity.Cita
import com.example.citasmedicas_app01.data.entity.Doctor
import com.example.citasmedicas_app01.data.entity.Paciente
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCitaScreen(
    onNavigateBack: () -> Unit,
    onSaveCita: (Cita) -> Unit,
    doctores: List<Doctor>,
    pacientes: List<Paciente>
) {
    var selectedPaciente by remember { mutableStateOf<Paciente?>(null) }
    var selectedDoctor by remember { mutableStateOf<Doctor?>(null) }
    var motivo by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("09:00") }
    var showPacienteDialog by remember { mutableStateOf(false) }
    var showDoctorDialog by remember { mutableStateOf(false) }
    
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Cita") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Crear Nueva Cita",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showPacienteDialog = true }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Paciente",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = selectedPaciente?.nombre ?: "Seleccionar paciente",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showDoctorDialog = true }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Doctor",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = selectedDoctor?.let { "${it.nombre} - ${it.especialidad}" } ?: "Seleccionar doctor",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            

            item {
                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { selectedDate = it },
                    label = { Text("Fecha (dd/MM/yyyy)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Ej: 25/12/2024") }
                )
            }
            

            item {
                OutlinedTextField(
                    value = selectedTime,
                    onValueChange = { selectedTime = it },
                    label = { Text("Hora (HH:mm)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Ej: 14:30") }
                )
            }
            

            item {
                OutlinedTextField(
                    value = motivo,
                    onValueChange = { motivo = it },
                    label = { Text("Motivo de la cita") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            

            item {
                OutlinedTextField(
                    value = notas,
                    onValueChange = { notas = it },
                    label = { Text("Notas adicionales (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }

            item {
                Button(
                    onClick = {
                        if (selectedPaciente != null && selectedDoctor != null && motivo.isNotBlank() && selectedDate.isNotBlank()) {
                            try {
                                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val fecha = dateFormat.parse(selectedDate)?.time ?: System.currentTimeMillis()
                                
                                val cita = Cita(
                                    pacienteId = selectedPaciente!!.id,
                                    doctorId = selectedDoctor!!.id,
                                    fecha = fecha,
                                    hora = selectedTime,
                                    motivo = motivo,
                                    notas = notas.ifBlank { null }
                                )
                                onSaveCita(cita)
                                onNavigateBack()
                            } catch (e: Exception) {

                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedPaciente != null && selectedDoctor != null && motivo.isNotBlank() && selectedDate.isNotBlank()
                ) {
                    Text("Guardar Cita")
                }
            }
        }
    }
    

    if (showPacienteDialog) {
        AlertDialog(
            onDismissRequest = { showPacienteDialog = false },
            title = { Text("Seleccionar Paciente") },
            text = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(pacientes) { paciente ->
                        Card(
                            onClick = {
                                selectedPaciente = paciente
                                showPacienteDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = paciente.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = paciente.telefono,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showPacienteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
    

    if (showDoctorDialog) {
        AlertDialog(
            onDismissRequest = { showDoctorDialog = false },
            title = { Text("Seleccionar Doctor") },
            text = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(doctores) { doctor ->
                        Card(
                            onClick = {
                                selectedDoctor = doctor
                                showDoctorDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = doctor.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = doctor.especialidad,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDoctorDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}


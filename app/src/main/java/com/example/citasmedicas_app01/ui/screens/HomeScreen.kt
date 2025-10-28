package com.example.citasmedicas_app01.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
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
fun HomeScreen(
    onNavigateToAddCita: () -> Unit,
    onNavigateToCitas: () -> Unit,
    onNavigateToPacientes: () -> Unit,
    onNavigateToDoctores: () -> Unit,
    citas: List<Cita>,
    doctores: List<Doctor>,
    pacientes: List<Paciente>,
    onDeleteCita: (Cita) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agenda de Citas Médicas") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddCita
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Cita")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Botones de navegación
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onNavigateToCitas,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ver Citas")
                }
                
                Button(
                    onClick = onNavigateToPacientes,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pacientes")
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onNavigateToDoctores,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Star, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Doctores")
                }
                
                Spacer(modifier = Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Próximas citas
            Text(
                text = "Próximas Citas",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            if (citas.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay citas programadas",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(citas.take(5)) { cita ->
                        val paciente = pacientes.find { it.id == cita.pacienteId }
                        val doctor = doctores.find { it.id == cita.doctorId }
                        
                        CitaCard(
                            cita = cita,
                            paciente = paciente,
                            doctor = doctor,
                            dateFormat = dateFormat,
                            timeFormat = timeFormat,
                            onDelete = { onDeleteCita(cita) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CitaCard(
    cita: Cita,
    paciente: Paciente?,
    doctor: Doctor?,
    dateFormat: SimpleDateFormat,
    timeFormat: SimpleDateFormat,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${dateFormat.format(Date(cita.fecha))} - ${cita.hora}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar")
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Paciente: ${paciente?.nombre ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = "Doctor: ${doctor?.nombre ?: "N/A"} - ${doctor?.especialidad ?: ""}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = "Motivo: ${cita.motivo}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = "Estado: ${cita.estado}",
                style = MaterialTheme.typography.bodySmall,
                color = when (cita.estado) {
                    "Programada" -> MaterialTheme.colorScheme.primary
                    "Completada" -> MaterialTheme.colorScheme.tertiary
                    "Cancelada" -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}


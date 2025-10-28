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
import com.example.citasmedicas_app01.data.entity.Cita
import com.example.citasmedicas_app01.data.entity.Doctor
import com.example.citasmedicas_app01.data.entity.Paciente
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitasListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddCita: () -> Unit,
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
                title = { Text("Lista de Citas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
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
        ) {
            if (citas.isEmpty()) {
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
                                text = "No hay citas programadas",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Toca el botón + para agregar una nueva cita",
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
                    items(citas) { cita ->
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




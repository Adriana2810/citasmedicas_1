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
    pacientes: List<Paciente>
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
                        PacienteCard(paciente = paciente)
                    }
                }
            }
        }
    }
}

@Composable
fun PacienteCard(paciente: Paciente) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
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
        }
    }
}


package com.example.citasmedicas_app01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.citasmedicas_app01.data.database.AppDatabase
import com.example.citasmedicas_app01.data.repository.CitasRepository
import com.example.citasmedicas_app01.ui.screens.*
import com.example.citasmedicas_app01.ui.theme.Citasmedicas_app01Theme
import com.example.citasmedicas_app01.ui.viewmodel.CitasViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val database = AppDatabase.getDatabase(this)
        val repository = CitasRepository(
            database.pacienteDao(),
            database.doctorDao(),
            database.citaDao()
        )
        
        setContent {
            Citasmedicas_app01Theme {
                CitasApp(repository = repository)
            }
        }
    }
}

@Composable
fun CitasApp(repository: CitasRepository) {
    val navController = rememberNavController()
    val viewModel = remember { CitasViewModel(repository) }
    
    val pacientes by viewModel.pacientes.collectAsState()
    val doctores by viewModel.doctores.collectAsState()
    val citas by viewModel.citas.collectAsState()

    LaunchedEffect(Unit) {
        // Insertar datos de ejemplo una sola vez
        viewModel.insertPaciente(
            com.example.citasmedicas_app01.data.entity.Paciente(
                nombre = "Juan Pérez",
                telefono = "555-1234",
                email = "juan@email.com"
            )
        )
        viewModel.insertPaciente(
            com.example.citasmedicas_app01.data.entity.Paciente(
                nombre = "María García",
                telefono = "555-5678",
                email = "maria@email.com"
            )
        )
        
        // Insertar doctores de ejemplo
        viewModel.insertDoctor(
            com.example.citasmedicas_app01.data.entity.Doctor(
                nombre = "Dr. Carlos López",
                especialidad = "Cardiología",
                telefono = "555-1111",
                email = "carlos@clinica.com",
                horarioInicio = "08:00",
                horarioFin = "16:00"
            )
        )
        viewModel.insertDoctor(
            com.example.citasmedicas_app01.data.entity.Doctor(
                nombre = "Dra. Ana Martínez",
                especialidad = "Pediatría",
                telefono = "555-2222",
                email = "ana@clinica.com",
                horarioInicio = "09:00",
                horarioFin = "17:00"
            )
        )
        viewModel.insertDoctor(
            com.example.citasmedicas_app01.data.entity.Doctor(
                nombre = "Dr. Miguel Rodríguez",
                especialidad = "Dermatología",
                telefono = "555-3333",
                email = "miguel@clinica.com",
                horarioInicio = "10:00",
                horarioFin = "18:00"
            )
        )
        viewModel.insertDoctor(
            com.example.citasmedicas_app01.data.entity.Doctor(
                nombre = "Dra. Laura Sánchez",
                especialidad = "Ginecología",
                telefono = "555-4444",
                email = "laura@clinica.com",
                horarioInicio = "08:30",
                horarioFin = "16:30"
            )
        )
        viewModel.insertDoctor(
            com.example.citasmedicas_app01.data.entity.Doctor(
                nombre = "Dr. Roberto García",
                especialidad = "Ortopedia",
                telefono = "555-5555",
                email = "roberto@clinica.com",
                horarioInicio = "07:00",
                horarioFin = "15:00"
            )
        )
    }
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToAddCita = { navController.navigate("add_cita") },
                onNavigateToCitas = { navController.navigate("citas_list") },
                onNavigateToPacientes = { navController.navigate("pacientes") },
                onNavigateToDoctores = { navController.navigate("doctores") },
                citas = citas,
                doctores = doctores,
                pacientes = pacientes,
                onDeleteCita = { cita -> viewModel.deleteCita(cita) }
            )
        }
        
        composable("add_cita") {
            AddCitaScreen(
                onNavigateBack = { navController.popBackStack() },
                onSaveCita = { cita -> viewModel.insertCita(cita) },
                doctores = doctores,
                pacientes = pacientes
            )
        }
        
        composable("citas_list") {
            CitasListScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddCita = { navController.navigate("add_cita") },
                citas = citas,
                doctores = doctores,
                pacientes = pacientes,
                onDeleteCita = { cita -> viewModel.deleteCita(cita) }
            )
        }
        
        composable("pacientes") {
            PacientesScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddPaciente = { navController.navigate("add_paciente") },
                pacientes = pacientes,
                onUpdatePaciente = { p -> viewModel.updatePaciente(p) },
                onDeletePaciente = { p -> viewModel.deletePaciente(p) },
                onNavigateToEditPaciente = { id -> navController.navigate("edit_paciente/$id") }
            )
        }
        
        composable("add_paciente") {
            AddPacienteScreen(
                onNavigateBack = { navController.popBackStack() },
                onSavePaciente = { paciente -> viewModel.insertPaciente(paciente) }
            )
        }
        
        composable("doctores") {
            DoctoresScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddDoctor = { navController.navigate("add_doctor") },
                doctores = doctores,
                onUpdateDoctor = { d -> viewModel.updateDoctor(d) },
                onDeleteDoctor = { d -> viewModel.deleteDoctor(d) },
                onNavigateToEditDoctor = { id -> navController.navigate("edit_doctor/$id") }
            )
        }
        
        composable("add_doctor") {
            AddDoctorScreen(
                onNavigateBack = { navController.popBackStack() },
                onSaveDoctor = { doctor -> viewModel.insertDoctor(doctor) }
            )
        }
        composable("edit_paciente/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            val paciente = id?.let { viewModel.getPacienteById(it) }
            if (paciente != null) {
                EditPacienteScreen(
                    paciente = paciente,
                    onNavigateBack = { navController.popBackStack() },
                    onUpdatePaciente = { p -> viewModel.updatePaciente(p) },
                    onDeletePaciente = { p -> viewModel.deletePaciente(p) }
                )
            } else {
                // Si no se encontró, regresar
                LaunchedEffect(Unit) { navController.popBackStack() }
            }
        }

        composable("edit_doctor/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            val doctor = id?.let { viewModel.getDoctorById(it) }
            if (doctor != null) {
                EditDoctorScreen(
                    doctor = doctor,
                    onNavigateBack = { navController.popBackStack() },
                    onUpdateDoctor = { d -> viewModel.updateDoctor(d) },
                    onDeleteDoctor = { d -> viewModel.deleteDoctor(d) }
                )
            } else {
                LaunchedEffect(Unit) { navController.popBackStack() }
            }
        }
    }
}
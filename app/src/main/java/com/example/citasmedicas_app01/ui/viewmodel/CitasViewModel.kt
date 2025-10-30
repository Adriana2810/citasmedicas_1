package com.example.citasmedicas_app01.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citasmedicas_app01.data.entity.Cita
import com.example.citasmedicas_app01.data.entity.Doctor
import com.example.citasmedicas_app01.data.entity.Paciente
import com.example.citasmedicas_app01.data.repository.CitasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CitasViewModel(
    private val repository: CitasRepository
) : ViewModel() {
    
    private val _pacientes = MutableStateFlow<List<Paciente>>(emptyList())
    val pacientes: StateFlow<List<Paciente>> = _pacientes.asStateFlow()
    
    private val _doctores = MutableStateFlow<List<Doctor>>(emptyList())
    val doctores: StateFlow<List<Doctor>> = _doctores.asStateFlow()
    
    private val _citas = MutableStateFlow<List<Cita>>(emptyList())
    val citas: StateFlow<List<Cita>> = _citas.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Cargar datos en paralelo
                launch {
                    repository.getAllPacientes().collectLatest { pacientes ->
                        _pacientes.value = pacientes
                    }
                }
                launch {
                    repository.getAllDoctores().collectLatest { doctores ->
                        _doctores.value = doctores
                    }
                }
                launch {
                    repository.getAllCitas().collectLatest { citas ->
                        _citas.value = citas
                    }
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun insertPaciente(paciente: Paciente) {
        viewModelScope.launch {
            repository.insertPaciente(paciente)
            // Los datos se actualizarán automáticamente por el Flow
        }
    }
    
    fun insertDoctor(doctor: Doctor) {
        viewModelScope.launch {
            repository.insertDoctor(doctor)
            // Los datos se actualizarán automáticamente por el Flow
        }
    }
    
    fun updatePaciente(paciente: Paciente) {
        viewModelScope.launch {
            repository.updatePaciente(paciente)
        }
    }
    
    fun updateDoctor(doctor: Doctor) {
        viewModelScope.launch {
            repository.updateDoctor(doctor)
        }
    }
    
    fun deletePaciente(paciente: Paciente) {
        viewModelScope.launch {
            repository.deletePaciente(paciente)
        }
    }
    
    fun deleteDoctor(doctor: Doctor) {
        viewModelScope.launch {
            repository.deleteDoctor(doctor)
        }
    }
    
    fun insertCita(cita: Cita) {
        viewModelScope.launch {
            repository.insertCita(cita)
            // Los datos se actualizarán automáticamente por el Flow
        }
    }
    
    fun updateCita(cita: Cita) {
        viewModelScope.launch {
            repository.updateCita(cita)
        }
    }
    
    fun deleteCita(cita: Cita) {
        viewModelScope.launch {
            repository.deleteCita(cita)
        }
    }
    
    fun getPacienteById(id: Long): Paciente? {
        return _pacientes.value.find { it.id == id }
    }
    
    fun getDoctorById(id: Long): Doctor? {
        return _doctores.value.find { it.id == id }
    }
}

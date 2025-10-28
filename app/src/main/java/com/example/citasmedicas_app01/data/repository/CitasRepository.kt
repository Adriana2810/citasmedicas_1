package com.example.citasmedicas_app01.data.repository

import com.example.citasmedicas_app01.data.dao.CitaDao
import com.example.citasmedicas_app01.data.dao.DoctorDao
import com.example.citasmedicas_app01.data.dao.PacienteDao
import com.example.citasmedicas_app01.data.entity.Cita
import com.example.citasmedicas_app01.data.entity.Doctor
import com.example.citasmedicas_app01.data.entity.Paciente
import kotlinx.coroutines.flow.Flow

class CitasRepository(
    private val pacienteDao: PacienteDao,
    private val doctorDao: DoctorDao,
    private val citaDao: CitaDao
) {
    // Pacientes
    fun getAllPacientes(): Flow<List<Paciente>> = pacienteDao.getAllPacientes()
    suspend fun getPacienteById(id: Long): Paciente? = pacienteDao.getPacienteById(id)
    fun searchPacientes(nombre: String): Flow<List<Paciente>> = pacienteDao.searchPacientes(nombre)
    suspend fun insertPaciente(paciente: Paciente): Long = pacienteDao.insertPaciente(paciente)
    suspend fun updatePaciente(paciente: Paciente) = pacienteDao.updatePaciente(paciente)
    suspend fun deletePaciente(paciente: Paciente) = pacienteDao.deletePaciente(paciente)
    
    // Doctores
    fun getAllDoctores(): Flow<List<Doctor>> = doctorDao.getAllDoctores()
    suspend fun getDoctorById(id: Long): Doctor? = doctorDao.getDoctorById(id)
    fun getDoctoresByEspecialidad(especialidad: String): Flow<List<Doctor>> = doctorDao.getDoctoresByEspecialidad(especialidad)
    suspend fun insertDoctor(doctor: Doctor): Long = doctorDao.insertDoctor(doctor)
    suspend fun updateDoctor(doctor: Doctor) = doctorDao.updateDoctor(doctor)
    suspend fun deleteDoctor(doctor: Doctor) = doctorDao.deleteDoctor(doctor)
    
    // Citas
    fun getAllCitas(): Flow<List<Cita>> = citaDao.getAllCitas()
    suspend fun getCitaById(id: Long): Cita? = citaDao.getCitaById(id)
    fun getCitasByFecha(fecha: Long): Flow<List<Cita>> = citaDao.getCitasByFecha(fecha)
    fun getCitasByPaciente(pacienteId: Long): Flow<List<Cita>> = citaDao.getCitasByPaciente(pacienteId)
    fun getCitasByDoctor(doctorId: Long): Flow<List<Cita>> = citaDao.getCitasByDoctor(doctorId)
    fun getCitasByEstado(estado: String): Flow<List<Cita>> = citaDao.getCitasByEstado(estado)
    suspend fun insertCita(cita: Cita): Long = citaDao.insertCita(cita)
    suspend fun updateCita(cita: Cita) = citaDao.updateCita(cita)
    suspend fun deleteCita(cita: Cita) = citaDao.deleteCita(cita)
}

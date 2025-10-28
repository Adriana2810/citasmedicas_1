package com.example.citasmedicas_app01.data.dao

import androidx.room.*
import com.example.citasmedicas_app01.data.entity.Paciente
import kotlinx.coroutines.flow.Flow

@Dao
interface PacienteDao {
    @Query("SELECT * FROM pacientes ORDER BY nombre ASC")
    fun getAllPacientes(): Flow<List<Paciente>>
    
    @Query("SELECT * FROM pacientes WHERE id = :id")
    suspend fun getPacienteById(id: Long): Paciente?
    
    @Query("SELECT * FROM pacientes WHERE nombre LIKE '%' || :nombre || '%'")
    fun searchPacientes(nombre: String): Flow<List<Paciente>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaciente(paciente: Paciente): Long
    
    @Update
    suspend fun updatePaciente(paciente: Paciente)
    
    @Delete
    suspend fun deletePaciente(paciente: Paciente)
    
    @Query("DELETE FROM pacientes WHERE id = :id")
    suspend fun deletePacienteById(id: Long)
}




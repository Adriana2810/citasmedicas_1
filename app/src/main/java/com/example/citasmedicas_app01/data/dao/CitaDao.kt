package com.example.citasmedicas_app01.data.dao

import androidx.room.*
import com.example.citasmedicas_app01.data.entity.Cita
import kotlinx.coroutines.flow.Flow

@Dao
interface CitaDao {
    @Query("SELECT * FROM citas ORDER BY fecha ASC, hora ASC")
    fun getAllCitas(): Flow<List<Cita>>
    
    @Query("SELECT * FROM citas WHERE id = :id")
    suspend fun getCitaById(id: Long): Cita?
    
    @Query("SELECT * FROM citas WHERE fecha = :fecha ORDER BY hora ASC")
    fun getCitasByFecha(fecha: Long): Flow<List<Cita>>
    
    @Query("SELECT * FROM citas WHERE pacienteId = :pacienteId ORDER BY fecha DESC")
    fun getCitasByPaciente(pacienteId: Long): Flow<List<Cita>>
    
    @Query("SELECT * FROM citas WHERE doctorId = :doctorId ORDER BY fecha ASC")
    fun getCitasByDoctor(doctorId: Long): Flow<List<Cita>>
    
    @Query("SELECT * FROM citas WHERE estado = :estado ORDER BY fecha ASC")
    fun getCitasByEstado(estado: String): Flow<List<Cita>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCita(cita: Cita): Long
    
    @Update
    suspend fun updateCita(cita: Cita)
    
    @Delete
    suspend fun deleteCita(cita: Cita)
    
    @Query("DELETE FROM citas WHERE id = :id")
    suspend fun deleteCitaById(id: Long)
}




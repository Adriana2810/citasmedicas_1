package com.example.citasmedicas_app01.data.dao

import androidx.room.*
import com.example.citasmedicas_app01.data.entity.Doctor
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {
    @Query("SELECT * FROM doctores ORDER BY nombre ASC")
    fun getAllDoctores(): Flow<List<Doctor>>
    
    @Query("SELECT * FROM doctores WHERE id = :id")
    suspend fun getDoctorById(id: Long): Doctor?
    
    @Query("SELECT * FROM doctores WHERE especialidad = :especialidad")
    fun getDoctoresByEspecialidad(especialidad: String): Flow<List<Doctor>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: Doctor): Long
    
    @Update
    suspend fun updateDoctor(doctor: Doctor)
    
    @Delete
    suspend fun deleteDoctor(doctor: Doctor)
    
    @Query("DELETE FROM doctores WHERE id = :id")
    suspend fun deleteDoctorById(id: Long)
}




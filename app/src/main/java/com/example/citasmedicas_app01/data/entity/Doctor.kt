package com.example.citasmedicas_app01.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctores")
data class Doctor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val especialidad: String,
    val telefono: String,
    val email: String? = null,
    val horarioInicio: String = "09:00",
    val horarioFin: String = "17:00"
)




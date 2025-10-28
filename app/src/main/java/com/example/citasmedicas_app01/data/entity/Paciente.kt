package com.example.citasmedicas_app01.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pacientes")
data class Paciente(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val telefono: String,
    val email: String? = null,
    val fechaCreacion: Long = System.currentTimeMillis()
)




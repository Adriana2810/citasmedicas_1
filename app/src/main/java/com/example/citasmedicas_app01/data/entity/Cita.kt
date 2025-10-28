package com.example.citasmedicas_app01.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "citas",
    foreignKeys = [
        ForeignKey(
            entity = Paciente::class,
            parentColumns = ["id"],
            childColumns = ["pacienteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Doctor::class,
            parentColumns = ["id"],
            childColumns = ["doctorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["pacienteId"]),
        Index(value = ["doctorId"])
    ]
)
data class Cita(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pacienteId: Long,
    val doctorId: Long,
    val fecha: Long, // Timestamp
    val hora: String, // Formato HH:mm
    val motivo: String,
    val estado: String = "Programada", // Programada, Completada, Cancelada
    val notas: String? = null,
    val fechaCreacion: Long = System.currentTimeMillis()
)


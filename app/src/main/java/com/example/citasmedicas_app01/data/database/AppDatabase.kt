package com.example.citasmedicas_app01.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import com.example.citasmedicas_app01.data.dao.CitaDao
import com.example.citasmedicas_app01.data.dao.DoctorDao
import com.example.citasmedicas_app01.data.dao.PacienteDao
import com.example.citasmedicas_app01.data.entity.Cita
import com.example.citasmedicas_app01.data.entity.Doctor
import com.example.citasmedicas_app01.data.entity.Paciente

@Database(
    entities = [Paciente::class, Doctor::class, Cita::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pacienteDao(): PacienteDao
    abstract fun doctorDao(): DoctorDao
    abstract fun citaDao(): CitaDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Agregar índices para foreign keys
                database.execSQL("CREATE INDEX IF NOT EXISTS index_citas_pacienteId ON citas(pacienteId)")
                database.execSQL("CREATE INDEX IF NOT EXISTS index_citas_doctorId ON citas(doctorId)")
            }
        }
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "citas_medicas_database"
                )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


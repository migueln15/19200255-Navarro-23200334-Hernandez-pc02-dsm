package com.example.dampc2.asteroides.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabla de la opción Asteroides (NeoWs).
 * Versión mínima creada en la base común: el responsable de la opción
 * agrega aquí el resto de columnas.
 */
@Entity(tableName = "asteroides")
data class AsteroideEntity(
    @PrimaryKey val id: String
)

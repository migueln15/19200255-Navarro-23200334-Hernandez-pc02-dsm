package com.example.dampc2.imagenes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabla de la opción Imágenes (APOD).
 * Versión mínima creada en la base común: el responsable de la opción
 * agrega aquí el resto de columnas.
 */
@Entity(tableName = "imagenes")
data class ApodEntity(
    @PrimaryKey val date: String
)

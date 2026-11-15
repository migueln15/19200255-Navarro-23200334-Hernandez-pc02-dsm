package com.example.dampc2.imagenes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabla local en Room para guardar las imágenes de APOD.
 */
@Entity(tableName = "imagenes")
data class ApodEntity(
    @PrimaryKey
    val date: String,
    val title: String,
    val explanation: String,
    val mediaType: String,
    val hdurl: String,
    val credit: String
)

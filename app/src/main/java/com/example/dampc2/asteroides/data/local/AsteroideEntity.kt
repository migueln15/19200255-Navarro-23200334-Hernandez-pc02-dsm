package com.example.dampc2.asteroides.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Tabla de la opción Asteroides (NeoWs). */
@Entity(tableName = "asteroides")
data class AsteroideEntity(
    @PrimaryKey val id: String,
    val nombre: String,
    val fechaAproximacion: String?,
    val fechaEpoch: Long?,
    val diametroMinMetros: Double?,
    val diametroMaxMetros: Double?,
    val velocidadKmH: Double?,
    val distanciaKm: Double?,
    val esPeligroso: Boolean,
    val esFavorito: Boolean = false
)

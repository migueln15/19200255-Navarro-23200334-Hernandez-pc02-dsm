package com.example.dampc2.asteroides.data.repository

import com.example.dampc2.asteroides.data.local.AsteroideEntity
import com.example.dampc2.asteroides.data.remote.AsteroideDto

/** Convierte el DTO de la API en la Entity de Room. Si no hay id, se descarta. */
fun AsteroideDto.toEntity(esFavorito: Boolean): AsteroideEntity? {
    val id = id ?: return null
    val aproximacion = aproximaciones?.firstOrNull()
    return AsteroideEntity(
        id = id,
        nombre = nombre ?: "Sin nombre",
        fechaAproximacion = aproximacion?.fecha,
        fechaEpoch = aproximacion?.fechaEpoch,
        diametroMinMetros = diametroEstimado?.metros?.minimo,
        diametroMaxMetros = diametroEstimado?.metros?.maximo,
        velocidadKmH = aproximacion?.velocidad?.kmPorHora?.toDoubleOrNull(),
        distanciaKm = aproximacion?.distancia?.kilometros?.toDoubleOrNull(),
        esPeligroso = esPeligroso ?: false,
        esFavorito = esFavorito
    )
}

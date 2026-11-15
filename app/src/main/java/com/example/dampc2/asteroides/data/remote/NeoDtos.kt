package com.example.dampc2.asteroides.data.remote

import com.google.gson.annotations.SerializedName

// Todos los campos son nulables para que la app no se cierre si la API manda datos incompletos.

/** Respuesta del feed: los asteroides vienen agrupados por fecha. */
data class NeoFeedDto(
    @SerializedName("near_earth_objects") val asteroidesPorFecha: Map<String, List<AsteroideDto>?>?
)

data class AsteroideDto(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val nombre: String?,
    @SerializedName("is_potentially_hazardous_asteroid") val esPeligroso: Boolean?,
    @SerializedName("estimated_diameter") val diametroEstimado: DiametroEstimadoDto?,
    @SerializedName("close_approach_data") val aproximaciones: List<AproximacionDto>?
)

data class DiametroEstimadoDto(
    @SerializedName("meters") val metros: RangoDiametroDto?
)

data class RangoDiametroDto(
    @SerializedName("estimated_diameter_min") val minimo: Double?,
    @SerializedName("estimated_diameter_max") val maximo: Double?
)

data class AproximacionDto(
    @SerializedName("close_approach_date") val fecha: String?,
    @SerializedName("epoch_date_close_approach") val fechaEpoch: Long?,
    @SerializedName("relative_velocity") val velocidad: VelocidadDto?,
    @SerializedName("miss_distance") val distancia: DistanciaDto?
)

/** La velocidad llega como texto. */
data class VelocidadDto(
    @SerializedName("kilometers_per_hour") val kmPorHora: String?
)

/** La distancia llega como texto. */
data class DistanciaDto(
    @SerializedName("kilometers") val kilometros: String?
)

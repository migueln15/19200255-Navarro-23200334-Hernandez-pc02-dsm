package com.example.dampc2.imagenes.data.remote.model

import com.example.dampc2.imagenes.data.local.ApodEntity
import com.google.gson.annotations.SerializedName

/**
 * Modelo de transferencia de datos (DTO) devuelto por la API APOD de la NASA.
 * Todos los campos son opcionales para evitar cierres ante datos incompletos.
 */
data class ApodDto(
    @SerializedName("date")
    val date: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("explanation")
    val explanation: String? = null,

    @SerializedName("media_type")
    val mediaType: String? = null,

    @SerializedName("hdurl")
    val hdurl: String? = null,

    @SerializedName("credit")
    val credit: String? = null
)

/**
 * Conversión explícita de un objeto DTO (API) a una Entity de Room.
 */
fun ApodDto.toEntity(): ApodEntity? {
    val validDate = date?.takeIf { it.isNotBlank() } ?: return null
    return ApodEntity(
        date = validDate,
        title = title?.takeIf { it.isNotBlank() } ?: "Sin título",
        explanation = explanation.orEmpty(),
        mediaType = mediaType.orEmpty().ifBlank { "image" },
        hdurl = hdurl.orEmpty(),
        credit = credit.orEmpty()
    )
}

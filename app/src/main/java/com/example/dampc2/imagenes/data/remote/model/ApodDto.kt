package com.example.dampc2.imagenes.data.remote.model

import com.example.dampc2.imagenes.data.local.ApodEntity
import com.google.gson.annotations.SerializedName

/**
 * Modelo de transferencia de datos (DTO) devuelto por la API APOD de la NASA.
 * Utiliza alternancia de nombres en @SerializedName para extraer el campo 'explanation'
 * tanto si la API responde con minúscula ("explanation") o mayúscula ("Explanation").
 */
data class ApodDto(
    @SerializedName(value = "date", alternate = ["Date"])
    val date: String? = null,

    @SerializedName(value = "title", alternate = ["Title"])
    val title: String? = null,

    @SerializedName(value = "explanation", alternate = ["Explanation"])
    val explanation: String? = null,

    @SerializedName(value = "media_type", alternate = ["mediaType", "Media_Type"])
    val mediaType: String? = null,

    @SerializedName(value = "hdurl", alternate = ["hdUrl", "HDUrl"])
    val hdurl: String? = null,

    @SerializedName(value = "credit", alternate = ["Credit"])
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

package com.example.dampc2.imagenes.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de transferencia de datos (DTO) devuelto por la API APOD de la NASA.
 * Campos según la especificación:
 * - date
 * - title
 * - explanation
 * - media_type
 * - hdurl
 * - credit
 */
data class ApodDto(
    @SerializedName("date")
    val date: String,

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

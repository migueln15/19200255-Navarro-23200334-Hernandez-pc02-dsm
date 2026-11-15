package com.example.dampc2.imagenes.data.remote

import com.example.dampc2.imagenes.data.remote.model.ApodDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interfaz de Retrofit para consultar el endpoint de APOD (Imagen Astronómica del Día).
 * Ruta: wp-json/wp/v2/apod-basic
 * Parámetros: start_date, end_date (en formato YYYY-MM-DD).
 * Nota: El parámetro api_key lo inyecta automáticamente OkHttpClient mediante Interceptor.
 */
interface ApodApiService {

    @GET("wp-json/wp/v2/apod-basic")
    suspend fun getApodImages(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): List<ApodDto>
}

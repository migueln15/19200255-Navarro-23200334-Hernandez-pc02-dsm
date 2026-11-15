package com.example.dampc2.imagenes.data.repository

import com.example.dampc2.data.remote.RetrofitClient
import com.example.dampc2.imagenes.data.local.ApodDao
import com.example.dampc2.imagenes.data.local.ApodEntity
import com.example.dampc2.imagenes.data.remote.ApodApiService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Repositorio para gestionar las imágenes de APOD (NASA).
 */
class ApodRepository(
    private val apiService: ApodApiService = RetrofitClient.apod.create(ApodApiService::class.java),
    private val apodDao: ApodDao
) {

    /**
     * Consulta las imágenes de APOD de los últimos 7 días (desde hace 6 días hasta hoy),
     * las guarda en la base de datos local y las devuelve ordenadas de la más reciente a la más antigua.
     */
    suspend fun fetchApodUltimos7Dias(): List<ApodEntity> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val hoy = LocalDate.now()
        val hace6Dias = hoy.minusDays(6)

        val endDate = hoy.format(formatter)
        val startDate = hace6Dias.format(formatter)

        return try {
            val dtoList = apiService.getApodImages(startDate = startDate, endDate = endDate)
            val entities = dtoList.map { dto ->
                ApodEntity(
                    date = dto.date,
                    title = dto.title ?: "Sin título",
                    explanation = dto.explanation ?: "",
                    mediaType = dto.mediaType ?: "image",
                    hdurl = dto.hdurl ?: "",
                    credit = dto.credit ?: ""
                )
            }.sortedByDescending { it.date }

            if (entities.isNotEmpty()) {
                apodDao.insertApodImages(entities)
            }
            entities
        } catch (e: Exception) {
            val localCache = apodDao.getApodImages()
            if (localCache.isNotEmpty()) {
                localCache
            } else {
                throw e
            }
        }
    }
}

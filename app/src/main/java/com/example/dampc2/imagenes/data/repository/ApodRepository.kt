package com.example.dampc2.imagenes.data.repository

import com.example.dampc2.data.remote.RetrofitClient
import com.example.dampc2.imagenes.data.local.ApodDao
import com.example.dampc2.imagenes.data.local.ApodEntity
import com.example.dampc2.imagenes.data.remote.ApodApiService
import com.example.dampc2.imagenes.data.remote.model.toEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Repositorio que gestiona el acceso a la API (Retrofit) y la base local (Room).
 * La pantalla lee siempre los datos desde Room (Fuente Única de Verdad).
 */
class ApodRepository(
    private val apiService: ApodApiService = RetrofitClient.apod.create(ApodApiService::class.java),
    private val apodDao: ApodDao
) {

    /**
     * Flujo reactivo continuo de imágenes leídas directamente de Room.
     */
    val imagenesFlow: Flow<List<ApodEntity>> = apodDao.getApodImagesFlow()

    /**
     * Consulta la API de la NASA para los últimos 7 días (desde hace 6 días hasta hoy),
     * realiza la conversión explícita DTO -> Entity y almacena los datos en Room.
     */
    suspend fun refrescarApodUltimos7Dias() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val hoy = LocalDate.now()
        val hace6Dias = hoy.minusDays(6)

        val endDate = hoy.format(formatter)
        val startDate = hace6Dias.format(formatter)

        val dtoList = apiService.getApodImages(startDate = startDate, endDate = endDate)
        // Conversión explícita de DTO a Entity
        val entities = dtoList.mapNotNull { it.toEntity() }

        if (entities.isNotEmpty()) {
            apodDao.insertApodImages(entities)
        }
    }
}

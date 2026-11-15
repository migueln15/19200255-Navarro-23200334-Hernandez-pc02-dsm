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
     * Flujo de las imágenes marcadas como favoritas en Room.
     */
    val favoritosFlow: Flow<List<ApodEntity>> = apodDao.observarFavoritos()

    /**
     * Obtiene una imagen por su fecha desde la base de datos local Room.
     */
    fun getApodByDate(date: String): Flow<ApodEntity?> = apodDao.getApodByDateFlow(date)

    /**
     * Cambia el estado de favorito de una imagen en Room.
     */
    suspend fun cambiarFavorito(apod: ApodEntity) {
        apodDao.updateFavorite(apod.date, !apod.isFavorite)
    }

    /**
     * Consulta la API de la NASA para los últimos 7 días (desde hace 6 días hasta hoy),
     * realiza la conversión explícita DTO -> Entity y almacena los datos en Room,
     * preservando el estado de favorito asignado previamente.
     */
    suspend fun refrescarApodUltimos7Dias() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val hoy = LocalDate.now()
        val hace6Dias = hoy.minusDays(6)

        val endDate = hoy.format(formatter)
        val startDate = hace6Dias.format(formatter)

        val dtoList = apiService.getApodImages(startDate = startDate, endDate = endDate)
        val fechasFavoritas = apodDao.obtenerFechasFavoritas().toSet()

        // Conversión explícita de DTO a Entity preservando el estado de favorito
        val entities = dtoList.mapNotNull { dto ->
            val entity = dto.toEntity() ?: return@mapNotNull null
            val esFavPrevio = entity.date in fechasFavoritas
            entity.copy(isFavorite = esFavPrevio)
        }

        if (entities.isNotEmpty()) {
            apodDao.insertApodImages(entities)
        }
    }
}

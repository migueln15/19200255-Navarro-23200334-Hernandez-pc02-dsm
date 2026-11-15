package com.example.dampc2.asteroides.data.repository

import com.example.dampc2.asteroides.data.local.AsteroideDao
import com.example.dampc2.asteroides.data.local.AsteroideEntity
import com.example.dampc2.asteroides.data.remote.NeoWsApi
import kotlinx.coroutines.flow.Flow

/** Gestiona la API de NeoWs y la tabla local de asteroides. */
class AsteroidesRepository(
    private val api: NeoWsApi,
    private val dao: AsteroideDao
) {

    fun observarAsteroides(desde: String): Flow<List<AsteroideEntity>> = dao.observarDesde(desde)

    fun observarAsteroide(id: String): Flow<AsteroideEntity?> = dao.observarPorId(id)

    fun observarFavoritos(): Flow<List<AsteroideEntity>> = dao.observarFavoritos()

    suspend fun cambiarFavorito(asteroide: AsteroideEntity) {
        dao.actualizarFavorito(asteroide.id, !asteroide.esFavorito)
    }

    /** Consulta la API y guarda el resultado en Room, sin perder los favoritos. */
    suspend fun actualizar(desde: String, hasta: String) {
        val respuesta = api.obtenerFeed(desde, hasta)
        val favoritos = dao.obtenerIdsFavoritos().toSet()
        val asteroides = respuesta.asteroidesPorFecha.orEmpty().values
            .flatMap { it.orEmpty() }
            .mapNotNull { dto -> dto.toEntity(esFavorito = dto.id in favoritos) }
        dao.insertarTodos(asteroides)
    }
}

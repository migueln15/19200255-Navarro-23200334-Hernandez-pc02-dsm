package com.example.dampc2.asteroides.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dampc2.asteroides.data.local.AsteroideEntity
import com.example.dampc2.asteroides.data.remote.NeoWsApi
import com.example.dampc2.asteroides.data.repository.AsteroidesRepository
import com.example.dampc2.data.local.AppDatabase
import com.example.dampc2.data.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

data class AsteroidesUiState(
    val cargando: Boolean = false,
    val asteroides: List<AsteroideEntity> = emptyList(),
    val soloPeligrosos: Boolean = false,
    val error: String? = null
)

class AsteroidesViewModel(private val repository: AsteroidesRepository) : ViewModel() {

    // Últimos 7 días: desde hace 6 días hasta hoy.
    private val hasta = LocalDate.now()
    private val desde = hasta.minusDays(6)

    private val cargando = MutableStateFlow(false)
    private val error = MutableStateFlow<String?>(null)
    private val soloPeligrosos = MutableStateFlow(false)

    /** La pantalla lee siempre desde Room. */
    val uiState: StateFlow<AsteroidesUiState> = combine(
        repository.observarAsteroides(desde.toString()),
        cargando,
        error,
        soloPeligrosos
    ) { asteroides, cargando, error, soloPeligrosos ->
        AsteroidesUiState(
            cargando = cargando,
            asteroides = if (soloPeligrosos) asteroides.filter { it.esPeligroso } else asteroides,
            soloPeligrosos = soloPeligrosos,
            error = error
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AsteroidesUiState(cargando = true))

    val favoritos: StateFlow<List<AsteroideEntity>> = repository.observarFavoritos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        actualizar()
    }

    fun asteroide(id: String): Flow<AsteroideEntity?> = repository.observarAsteroide(id)

    fun cambiarFavorito(asteroide: AsteroideEntity) {
        viewModelScope.launch { repository.cambiarFavorito(asteroide) }
    }

    fun cambiarFiltro(activo: Boolean) {
        soloPeligrosos.value = activo
    }

    /** Consulta la API y guarda en Room. */
    fun actualizar() {
        viewModelScope.launch {
            cargando.value = true
            error.value = null
            try {
                repository.actualizar(desde.toString(), hasta.toString())
            } catch (e: Exception) {
                error.value = "No se pudo consultar la API de asteroides."
            } finally {
                cargando.value = false
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY]!!
                val repository = AsteroidesRepository(
                    api = RetrofitClient.neoWs.create(NeoWsApi::class.java),
                    dao = AppDatabase.getInstance(app).asteroideDao()
                )
                AsteroidesViewModel(repository)
            }
        }
    }
}

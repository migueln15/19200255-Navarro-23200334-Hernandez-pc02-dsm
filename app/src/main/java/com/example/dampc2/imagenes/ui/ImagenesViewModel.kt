package com.example.dampc2.imagenes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dampc2.data.local.AppDatabase
import com.example.dampc2.imagenes.data.local.ApodEntity
import com.example.dampc2.imagenes.data.repository.ApodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel que expone el estado a la pantalla con StateFlow.
 * Las pantallas leen siempre los datos provenientes de la base local Room.
 */
class ImagenesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ApodRepository = ApodRepository(
        apodDao = AppDatabase.getInstance(application).apodDao()
    )

    private val _uiState = MutableStateFlow<ImagenesUiState>(ImagenesUiState.Loading)
    val uiState: StateFlow<ImagenesUiState> = _uiState.asStateFlow()

    val favoritos: StateFlow<List<ApodEntity>> = repository.favoritosFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        observarYRefrescar()
    }

    private fun observarYRefrescar() {
        // 1. Escuchar los datos directamente de la base local (Room)
        viewModelScope.launch {
            repository.imagenesFlow.collect { listaRoom ->
                _uiState.value = ImagenesUiState.Success(listaRoom)
            }
        }

        // 2. Cargar/Refrescar desde la API y guardar en Room
        cargarImagenes()
    }

    fun getApodByDate(date: String): Flow<ApodEntity?> {
        return repository.getApodByDate(date)
    }

    fun cambiarFavorito(apod: ApodEntity) {
        viewModelScope.launch {
            repository.cambiarFavorito(apod)
        }
    }

    fun cargarImagenes() {
        viewModelScope.launch {
            val actual = _uiState.value
            if (actual !is ImagenesUiState.Success || actual.imagenes.isEmpty()) {
                _uiState.value = ImagenesUiState.Loading
            }
            try {
                repository.refrescarApodUltimos7Dias()
                // Al guardarse en Room, repository.imagenesFlow emite la lista automáticamente a la UI.
            } catch (e: Exception) {
                val listaActual = (_uiState.value as? ImagenesUiState.Success)?.imagenes.orEmpty()
                if (listaActual.isEmpty()) {
                    _uiState.value = ImagenesUiState.Error(
                        e.localizedMessage ?: "Ocurrió un error al consultar las imágenes de la NASA."
                    )
                }
            }
        }
    }
}

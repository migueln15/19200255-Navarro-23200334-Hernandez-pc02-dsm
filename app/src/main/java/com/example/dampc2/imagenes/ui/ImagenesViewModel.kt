package com.example.dampc2.imagenes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dampc2.data.local.AppDatabase
import com.example.dampc2.imagenes.data.repository.ApodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de gestionar el estado de la pantalla de Imágenes de APOD.
 */
class ImagenesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ApodRepository

    private val _uiState = MutableStateFlow<ImagenesUiState>(ImagenesUiState.Loading)
    val uiState: StateFlow<ImagenesUiState> = _uiState.asStateFlow()

    init {
        val apodDao = AppDatabase.getInstance(application).apodDao()
        repository = ApodRepository(apodDao = apodDao)
        cargarImagenes()
    }

    fun cargarImagenes() {
        viewModelScope.launch {
            _uiState.value = ImagenesUiState.Loading
            try {
                val imagenes = repository.fetchApodUltimos7Dias()
                _uiState.value = ImagenesUiState.Success(imagenes)
            } catch (e: Exception) {
                _uiState.value = ImagenesUiState.Error(
                    e.localizedMessage ?: "Ocurrió un error al cargar las imágenes astronómicas."
                )
            }
        }
    }
}

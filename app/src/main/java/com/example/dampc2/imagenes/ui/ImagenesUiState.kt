package com.example.dampc2.imagenes.ui

import com.example.dampc2.imagenes.data.local.ApodEntity

/**
 * Estados de la interfaz de usuario para la pantalla de Imágenes APOD.
 */
sealed interface ImagenesUiState {
    data object Loading : ImagenesUiState
    data class Success(val imagenes: List<ApodEntity>) : ImagenesUiState
    data class Error(val message: String) : ImagenesUiState
}

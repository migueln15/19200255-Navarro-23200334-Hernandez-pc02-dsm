package com.example.dampc2.imagenes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

/** Rutas de la opción Imágenes. La navegación interna (lista, detalle, favoritos) va aquí. */
object ImagenesRoutes {
    const val GRAPH = "imagenes"
    const val LISTA = "imagenes/lista"
}

/** Grafo de navegación de la opción Imágenes. */
fun NavGraphBuilder.imagenesGraph(navController: NavHostController) {
    navigation(route = ImagenesRoutes.GRAPH, startDestination = ImagenesRoutes.LISTA) {
        composable(ImagenesRoutes.LISTA) {
            ImagenesPendiente()
        }
    }
}

@Composable
private fun ImagenesPendiente() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Imágenes")
    }
}

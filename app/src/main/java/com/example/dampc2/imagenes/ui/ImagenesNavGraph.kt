package com.example.dampc2.imagenes.ui

import androidx.lifecycle.viewmodel.compose.viewModel
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
            val viewModel: ImagenesViewModel = viewModel()
            ImagenesScreen(viewModel = viewModel)
        }
    }
}

package com.example.dampc2.imagenes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

/** Rutas de la opción Imágenes. */
object ImagenesRoutes {
    const val GRAPH = "imagenes"
    const val LISTA = "imagenes/lista"
    const val DETALLE = "imagenes/detalle/{date}"
    const val FAVORITOS = "imagenes/favoritos"

    fun crearRutaDetalle(date: String): String = "imagenes/detalle/$date"
}

/** Grafo de navegación de la opción Imágenes. */
fun NavGraphBuilder.imagenesGraph(navController: NavHostController) {
    navigation(route = ImagenesRoutes.GRAPH, startDestination = ImagenesRoutes.LISTA) {
        composable(ImagenesRoutes.LISTA) { entry ->
            val viewModel = imagenesViewModel(navController, entry)
            ImagenesScreen(
                viewModel = viewModel,
                onApodClick = { apod ->
                    navController.navigate(ImagenesRoutes.crearRutaDetalle(apod.date))
                },
                onMisFavoritosClick = {
                    navController.navigate(ImagenesRoutes.FAVORITOS)
                }
            )
        }
        composable(ImagenesRoutes.FAVORITOS) { entry ->
            val viewModel = imagenesViewModel(navController, entry)
            MisImagenesFavoritasScreen(
                viewModel = viewModel,
                onApodClick = { apod ->
                    navController.navigate(ImagenesRoutes.crearRutaDetalle(apod.date))
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = ImagenesRoutes.DETALLE,
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { entry ->
            val date = entry.arguments?.getString("date").orEmpty()
            val viewModel = imagenesViewModel(navController, entry)
            ImagenesDetalleScreen(
                date = date,
                viewModel = viewModel,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}

/** ViewModel compartido para todas las pantallas del grafo de Imágenes. */
@Composable
private fun imagenesViewModel(
    navController: NavHostController,
    entry: NavBackStackEntry
): ImagenesViewModel {
    val parentEntry = remember(entry) { navController.getBackStackEntry(ImagenesRoutes.GRAPH) }
    return viewModel(parentEntry)
}

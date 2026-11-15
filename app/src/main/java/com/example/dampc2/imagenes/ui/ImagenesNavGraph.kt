package com.example.dampc2.imagenes.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

/** Rutas de la opción Imágenes. La navegación interna (lista, detalle) va aquí. */
object ImagenesRoutes {
    const val GRAPH = "imagenes"
    const val LISTA = "imagenes/lista"
    const val DETALLE = "imagenes/detalle/{date}"

    fun crearRutaDetalle(date: String): String = "imagenes/detalle/$date"
}

/** Grafo de navegación de la opción Imágenes. */
fun NavGraphBuilder.imagenesGraph(navController: NavHostController) {
    navigation(route = ImagenesRoutes.GRAPH, startDestination = ImagenesRoutes.LISTA) {
        composable(ImagenesRoutes.LISTA) {
            val viewModel: ImagenesViewModel = viewModel()
            ImagenesScreen(
                viewModel = viewModel,
                onApodClick = { apod ->
                    navController.navigate(ImagenesRoutes.crearRutaDetalle(apod.date))
                }
            )
        }
        composable(
            route = ImagenesRoutes.DETALLE,
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date").orEmpty()
            val viewModel: ImagenesViewModel = viewModel()
            ImagenesDetalleScreen(
                date = date,
                viewModel = viewModel,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}

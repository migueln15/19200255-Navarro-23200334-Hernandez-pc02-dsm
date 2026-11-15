package com.example.dampc2.asteroides.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

/** Rutas de la opción Asteroides. */
object AsteroidesRoutes {
    const val GRAPH = "asteroides"
    const val LISTA = "asteroides/lista"
    const val DETALLE = "asteroides/detalle/{id}"

    fun detalle(id: String) = "asteroides/detalle/$id"
}

/** Grafo de navegación de la opción Asteroides. */
fun NavGraphBuilder.asteroidesGraph(navController: NavHostController) {
    navigation(route = AsteroidesRoutes.GRAPH, startDestination = AsteroidesRoutes.LISTA) {
        composable(AsteroidesRoutes.LISTA) { entry ->
            AsteroidesListaScreen(
                viewModel = asteroidesViewModel(navController, entry),
                onAsteroideClick = { id -> navController.navigate(AsteroidesRoutes.detalle(id)) }
            )
        }
        composable(AsteroidesRoutes.DETALLE) { entry ->
            AsteroideDetalleScreen(
                id = entry.arguments?.getString("id").orEmpty(),
                viewModel = asteroidesViewModel(navController, entry),
                onVolver = { navController.popBackStack() }
            )
        }
    }
}

/** Un solo ViewModel compartido por todas las pantallas de la opción. */
@Composable
private fun asteroidesViewModel(
    navController: NavHostController,
    entry: NavBackStackEntry
): AsteroidesViewModel {
    val grafo = remember(entry) { navController.getBackStackEntry(AsteroidesRoutes.GRAPH) }
    return viewModel(grafo, factory = AsteroidesViewModel.Factory)
}

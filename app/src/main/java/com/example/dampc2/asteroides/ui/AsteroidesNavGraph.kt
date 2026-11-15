package com.example.dampc2.asteroides.ui

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

/** Rutas de la opción Asteroides. La navegación interna (lista, detalle, favoritos) va aquí. */
object AsteroidesRoutes {
    const val GRAPH = "asteroides"
    const val LISTA = "asteroides/lista"
}

/** Grafo de navegación de la opción Asteroides. */
fun NavGraphBuilder.asteroidesGraph(navController: NavHostController) {
    navigation(route = AsteroidesRoutes.GRAPH, startDestination = AsteroidesRoutes.LISTA) {
        composable(AsteroidesRoutes.LISTA) {
            AsteroidesPendiente()
        }
    }
}

@Composable
private fun AsteroidesPendiente() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Asteroides")
    }
}

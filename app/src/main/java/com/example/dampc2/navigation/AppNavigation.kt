package com.example.dampc2.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dampc2.asteroides.ui.AsteroidesRoutes
import com.example.dampc2.asteroides.ui.asteroidesGraph
import com.example.dampc2.imagenes.ui.ImagenesRoutes
import com.example.dampc2.imagenes.ui.imagenesGraph

/** Opciones de la barra de navegación inferior. */
private enum class OpcionInferior(val route: String, val label: String, val icon: ImageVector) {
    IMAGENES(ImagenesRoutes.GRAPH, "Imágenes", Icons.Filled.Image),
    ASTEROIDES(AsteroidesRoutes.GRAPH, "Asteroides", Icons.Filled.Public)
}

/**
 * Navegación principal: barra inferior con Imágenes y Asteroides.
 * Cada opción define su propio grafo (navegación interna) en su paquete.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val destinoActual = backStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                OpcionInferior.entries.forEach { opcion ->
                    val seleccionada = destinoActual?.hierarchy?.any { it.route == opcion.route } == true
                    NavigationBarItem(
                        selected = seleccionada,
                        onClick = {
                            navController.navigate(opcion.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(opcion.icon, contentDescription = opcion.label) },
                        label = { Text(opcion.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ImagenesRoutes.GRAPH,
            modifier = Modifier.padding(innerPadding)
        ) {
            imagenesGraph(navController)
            asteroidesGraph(navController)
        }
    }
}

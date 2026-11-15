package com.example.dampc2.asteroides.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dampc2.asteroides.data.local.AsteroideEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsteroidesListaScreen(
    viewModel: AsteroidesViewModel,
    onAsteroideClick: (String) -> Unit,
    onMisAsteroidesClick: () -> Unit
) {
    val estado by viewModel.uiState.collectAsState()

    // La barra inferior ya aplica los márgenes del sistema, por eso WindowInsets(0).
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asteroides") },
                actions = { TextButton(onClick = onMisAsteroidesClick) { Text("Mis asteroides") } },
                windowInsets = WindowInsets(0)
            )
        },
        contentWindowInsets = WindowInsets(0)
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            FiltroPeligrosos(activo = estado.soloPeligrosos, onCambio = viewModel::cambiarFiltro)
            ContenidoLista(
                estado = estado,
                onAsteroideClick = onAsteroideClick,
                onFavoritoClick = viewModel::cambiarFavorito
            )
        }
    }
}

@Composable
private fun FiltroPeligrosos(activo: Boolean, onCambio: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Solo potencialmente peligrosos", modifier = Modifier.weight(1f))
        Switch(checked = activo, onCheckedChange = onCambio)
    }
}

/** Indicador de carga, mensaje si no hay datos o la lista. */
@Composable
private fun ContenidoLista(
    estado: AsteroidesUiState,
    onAsteroideClick: (String) -> Unit,
    onFavoritoClick: (AsteroideEntity) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            estado.cargando -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            estado.asteroides.isEmpty() -> Text(
                text = estado.error ?: "No hay asteroides para mostrar.",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center).padding(24.dp)
            )

            else -> LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(estado.asteroides, key = { it.id }) { asteroide ->
                    AsteroideItem(
                        asteroide = asteroide,
                        onClick = { onAsteroideClick(asteroide.id) },
                        onFavoritoClick = { onFavoritoClick(asteroide) }
                    )
                }
            }
        }
    }
}

package com.example.dampc2.asteroides.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dampc2.asteroides.data.local.AsteroideEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsteroideDetalleScreen(id: String, viewModel: AsteroidesViewModel, onVolver: () -> Unit) {
    val asteroide by remember(id) { viewModel.asteroide(id) }.collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                windowInsets = WindowInsets(0)
            )
        },
        contentWindowInsets = WindowInsets(0)
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            val actual = asteroide
            if (actual == null) {
                Text(text = "Asteroide no encontrado.", modifier = Modifier.align(Alignment.Center))
            } else {
                DatosAsteroide(actual)
            }
        }
    }
}

@Composable
private fun DatosAsteroide(asteroide: AsteroideEntity) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = asteroide.nombre, style = MaterialTheme.typography.headlineSmall)
        if (asteroide.esPeligroso) EtiquetaPeligroso()
        Text(text = "Id: ${asteroide.id}")
        Text(text = "Fecha de aproximación: ${asteroide.fechaAproximacion ?: "Sin dato"}")
        Text(text = "Diámetro mínimo estimado: ${asteroide.diametroMinMetros.conUnidad("m")}")
        Text(text = "Diámetro máximo estimado: ${asteroide.diametroMaxMetros.conUnidad("m")}")
        Text(text = "Velocidad: ${asteroide.velocidadKmH.conUnidad("km/h")}")
        Text(text = "Distancia: ${asteroide.distanciaKm.conUnidad("km")}")
        Text(text = "Potencialmente peligroso: ${if (asteroide.esPeligroso) "Sí" else "No"}")
    }
}

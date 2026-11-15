package com.example.dampc2.asteroides.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dampc2.asteroides.data.local.AsteroideEntity

@Composable
fun AsteroideItem(
    asteroide: AsteroideEntity,
    onClick: () -> Unit,
    onFavoritoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 16.dp, end = 4.dp)) {
            Column(modifier = Modifier.weight(1f).padding(top = 8.dp)) {
                Text(text = asteroide.nombre, style = MaterialTheme.typography.titleMedium)
                if (asteroide.esPeligroso) {
                    Spacer(modifier = Modifier.height(4.dp))
                    EtiquetaPeligroso()
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Fecha de aproximación: ${asteroide.fechaAproximacion ?: "Sin dato"}")
                Text(text = "Diámetro máximo: ${asteroide.diametroMaxMetros.conUnidad("m")}")
                Text(text = "Velocidad: ${asteroide.velocidadKmH.conUnidad("km/h")}")
                Text(text = "Distancia: ${asteroide.distanciaKm.conUnidad("km")}")
            }
            BotonFavorito(esFavorito = asteroide.esFavorito, onClick = onFavoritoClick)
        }
    }
}

/** Estrella llena si es favorito, vacía si no. */
@Composable
fun BotonFavorito(esFavorito: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (esFavorito) Icons.Filled.Star else Icons.Filled.StarBorder,
            contentDescription = if (esFavorito) "Quitar de favoritos" else "Marcar como favorito",
            tint = if (esFavorito) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun EtiquetaPeligroso() {
    Surface(
        color = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = "Potencialmente peligroso",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

package com.example.dampc2.asteroides.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dampc2.asteroides.data.local.AsteroideEntity

@Composable
fun AsteroideItem(asteroide: AsteroideEntity, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = asteroide.nombre, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Fecha de aproximación: ${asteroide.fechaAproximacion ?: "Sin dato"}")
            Text(text = "Diámetro máximo: ${asteroide.diametroMaxMetros.conUnidad("m")}")
            Text(text = "Velocidad: ${asteroide.velocidadKmH.conUnidad("km/h")}")
            Text(text = "Distancia: ${asteroide.distanciaKm.conUnidad("km")}")
        }
    }
}

package com.example.dampc2.asteroides.ui

import java.text.DecimalFormat

private val formato = DecimalFormat("#,##0.##")

/** Número con máximo dos decimales y su unidad. */
fun Double?.conUnidad(unidad: String): String =
    if (this == null) "Sin dato" else "${formato.format(this)} $unidad"

package com.example.farmaciasmiranda.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.farmaciasmiranda.model.Medicamento
import androidx.compose.ui.graphics.Color

@Composable
fun MedicamentoCard(medicamento: Medicamento, stock: Int) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(medicamento.categoria.nombre, style = MaterialTheme.typography.labelLarge)
            Text(medicamento.nombre, style = MaterialTheme.typography.titleLarge)
            Text(medicamento.descripcion)
            Text(medicamento.presentacion)
            Text(medicamento.laboratorio)
            Text(if (medicamento.requierePrescripcion()) "Requiere receta" else "Sin receta")
            Text(medicamento.obtenerPrecioFormateado(), style = MaterialTheme.typography.titleMedium)
            val (estado, color) = when {
                !medicamento.verificarDisponibilidad(1, stock) -> "Agotado" to Color(0xFF982C36)
                medicamento.necesitaReposicion(stock) -> "Stock bajo" to Color(0xFF914200)
                else -> "Disponible" to Color(0xFF216348)
            }
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Surface(color = color, contentColor = Color.White, shape = MaterialTheme.shapes.small) {
                    Text(estado, Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                }
                Text("$stock unidades")
            }
        }
    }
}

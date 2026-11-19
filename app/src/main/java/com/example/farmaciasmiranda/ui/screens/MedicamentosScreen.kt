package com.example.farmaciasmiranda.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.*
import com.example.farmaciasmiranda.data.DatosFarmacia
import com.example.farmaciasmiranda.ui.components.MedicamentoCard

@Composable
fun MedicamentosScreen(estado: LazyListState) {
    LazyColumn(
        state = estado,
        modifier = Modifier.widthIn(max = 680.dp).fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("Catálogo de medicamentos", style = MaterialTheme.typography.headlineSmall) }
        items(DatosFarmacia.farmacia.listarMedicamentos(), key = { it.id }) { medicamento ->
            MedicamentoCard(medicamento, DatosFarmacia.farmacia.inventario.consultarStock(medicamento))
        }
    }
}

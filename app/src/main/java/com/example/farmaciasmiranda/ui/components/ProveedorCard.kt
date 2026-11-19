package com.example.farmaciasmiranda.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.farmaciasmiranda.model.Proveedor

@Composable
fun ProveedorCard(proveedor: Proveedor, cantidad: Int) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(proveedor.nombre, style = MaterialTheme.typography.titleLarge)
            Text("NIT ${proveedor.nit}")
            Text(proveedor.obtenerDatosContacto())
            Text(proveedor.direccion)
            HorizontalDivider()
            Text(if (cantidad == 1) "1 medicamento asociado" else "$cantidad medicamentos asociados",
                style = MaterialTheme.typography.labelLarge)
        }
    }
}

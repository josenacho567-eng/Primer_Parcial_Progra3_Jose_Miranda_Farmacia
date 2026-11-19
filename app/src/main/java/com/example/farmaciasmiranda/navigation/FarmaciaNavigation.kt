package com.example.farmaciasmiranda.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.farmaciasmiranda.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmaciaNavigation() {
    var verProveedores by rememberSaveable { mutableStateOf(false) }
    val catalogo = rememberLazyListState()
    val directorio = rememberLazyListState()
    BackHandler(enabled = verProveedores) { verProveedores = false }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (verProveedores) "Proveedores" else "Farmacia") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF155943), titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar {
                listOf("Medicamentos", "Proveedores").forEachIndexed { indice, nombre ->
                    val proveedores = indice == 1
                    NavigationBarItem(
                        selected = verProveedores == proveedores,
                        onClick = { verProveedores = proveedores },
                        icon = { androidx.compose.foundation.Canvas(Modifier.size(24.dp)) {
                            val color = Color(0xFF34785F)
                            if (proveedores) {
                                drawRect(color, size = androidx.compose.ui.geometry.Size(size.width * .45f, size.height))
                                drawRect(color, topLeft = androidx.compose.ui.geometry.Offset(size.width * .55f, size.height * .3f),
                                    size = androidx.compose.ui.geometry.Size(size.width * .45f, size.height * .7f))
                            } else {
                                drawLine(color, center.copy(x = 0f), center.copy(x = size.width), 5.dp.toPx())
                                drawLine(color, center.copy(y = 0f), center.copy(y = size.height), 5.dp.toPx())
                            }
                        } },
                        label = { Text(nombre) }
                    )
                }
            }
        }
    ) { margenes ->
        Box(Modifier.fillMaxSize().padding(margenes), contentAlignment = Alignment.TopCenter) {
            if (verProveedores) ProveedoresScreen(directorio) else MedicamentosScreen(catalogo)
        }
    }
}

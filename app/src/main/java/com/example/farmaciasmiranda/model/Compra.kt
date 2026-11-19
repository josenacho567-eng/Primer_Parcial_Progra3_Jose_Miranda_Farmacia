package com.example.farmaciasmiranda.model

import java.time.LocalDate

class Compra(val id: Int, val fecha: LocalDate, val proveedor: Proveedor) {
    private val detalles = mutableListOf<DetalleCompra>()
    var estado: EstadoCompra = EstadoCompra.PENDIENTE
        private set

    fun agregarDetalle(detalle: DetalleCompra): Boolean {
        if (estado != EstadoCompra.PENDIENTE || detalles.any { it.id == detalle.id } ||
            detalle.fechaFabricacion > fecha) return false
        return detalles.add(detalle)
    }
    fun calcularTotal(): Double = detalles.sumOf { it.calcularSubtotal() }
    fun obtenerResumen(): String = "Compra $id · ${proveedor.nombre} · $estado"
    fun medicamentosSuministrados(): List<Medicamento> =
        if (estado == EstadoCompra.RECIBIDA) detalles.map { it.medicamento }.distinctBy { it.id } else emptyList()

    fun registrarIngreso(inventario: Inventario): Boolean {
        if (estado != EstadoCompra.PENDIENTE || detalles.isEmpty() || fecha > LocalDate.now()) return false
        if (!inventario.ingresarLotes(detalles.map { it.generarLote() })) return false
        estado = EstadoCompra.RECIBIDA
        return true
    }
    fun cancelarCompra(): Boolean {
        if (estado != EstadoCompra.PENDIENTE) return false
        estado = EstadoCompra.CANCELADA
        return true
    }
}

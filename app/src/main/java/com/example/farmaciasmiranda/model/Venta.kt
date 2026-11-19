package com.example.farmaciasmiranda.model

import java.time.LocalDate
import java.time.LocalDateTime

class Venta(val id: Int, val fecha: LocalDateTime, val cliente: Cliente?, val empleado: Empleado) {
    private val detalles = mutableListOf<DetalleVenta>()
    var estado: EstadoVenta = EstadoVenta.PENDIENTE
        private set

    fun agregarDetalle(detalle: DetalleVenta): Boolean {
        if (estado != EstadoVenta.PENDIENTE || detalles.any { it.id == detalle.id }) return false
        return detalles.add(detalle)
    }
    fun eliminarDetalle(idDetalle: Int): Boolean =
        estado == EstadoVenta.PENDIENTE && detalles.removeAll { it.id == idDetalle }

    fun calcularTotal(): Double = detalles.sumOf { it.calcularSubtotal() }
    fun calcularCantidadProductos(): Long = detalles.sumOf { it.cantidad.toLong() }
    fun obtenerResumen(): String = "Venta $id · $estado · Bs ${calcularTotal()}"

    fun confirmarVenta(inventario: Inventario): Boolean {
        val hoy = LocalDate.now()
        if (estado != EstadoVenta.PENDIENTE || detalles.isEmpty() || fecha.toLocalDate() != hoy) return false
        if (!inventario.despachar(detalles, hoy)) return false
        estado = EstadoVenta.CONFIRMADA
        return true
    }
    fun cancelarVenta(): Boolean {
        if (estado != EstadoVenta.PENDIENTE) return false
        estado = EstadoVenta.CANCELADA
        return true
    }
}

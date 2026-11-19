package com.example.farmaciasmiranda.model

class DetalleVenta(val id: Int, val medicamento: Medicamento, val cantidad: Int,
                   val precioUnitario: Double = medicamento.precio) {
    init { require(cantidad > 0 && precioUnitario.isFinite() && precioUnitario >= 0) }
    fun calcularSubtotal(): Double = cantidad * precioUnitario
    fun obtenerInformacion(): String = "${medicamento.nombre} × $cantidad = ${calcularSubtotal()}"
}

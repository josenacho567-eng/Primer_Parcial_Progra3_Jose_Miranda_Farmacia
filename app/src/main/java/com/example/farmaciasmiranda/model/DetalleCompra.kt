package com.example.farmaciasmiranda.model

import java.time.LocalDate

class DetalleCompra(val id: Int, val medicamento: Medicamento, val cantidad: Int, val costoUnitario: Double,
                    val numeroLote: String, val fechaFabricacion: LocalDate, val fechaVencimiento: LocalDate) {
    init {
        require(cantidad > 0 && costoUnitario.isFinite() && costoUnitario >= 0)
        require(numeroLote.isNotBlank() && fechaFabricacion <= fechaVencimiento)
    }
    fun calcularSubtotal(): Double = cantidad * costoUnitario
    fun generarLote(): LoteMedicamento = LoteMedicamento(id, numeroLote, medicamento, fechaFabricacion,
        fechaVencimiento, cantidad, costoUnitario)
}

package com.example.farmaciasmiranda.model

import java.time.LocalDate

class LoteMedicamento(val id: Int, val numeroLote: String, val medicamento: Medicamento,
                      val fechaFabricacion: LocalDate, val fechaVencimiento: LocalDate,
                      cantidadDisponible: Int, val precioCompra: Double) {
    var cantidadDisponible: Int = cantidadDisponible
        private set

    init {
        require(numeroLote.isNotBlank() && fechaFabricacion <= fechaVencimiento)
        require(cantidadDisponible >= 0 && precioCompra.isFinite() && precioCompra >= 0)
    }

    fun estaVencido(fechaActual: LocalDate): Boolean = fechaVencimiento < fechaActual
    fun estaPorVencer(fechaActual: LocalDate, dias: Long): Boolean =
        fechaVencimiento.toEpochDay() - fechaActual.toEpochDay() in 0L..dias

    fun descontarCantidad(cantidad: Int): Boolean {
        if (cantidad !in 1..cantidadDisponible) return false
        cantidadDisponible -= cantidad
        return true
    }

    fun aumentarCantidad(cantidad: Int): Boolean {
        if (cantidad !in 1..(Int.MAX_VALUE - cantidadDisponible)) return false
        cantidadDisponible += cantidad
        return true
    }

    fun obtenerInformacion(): String = "$numeroLote · ${medicamento.nombre} · $fechaVencimiento"
}

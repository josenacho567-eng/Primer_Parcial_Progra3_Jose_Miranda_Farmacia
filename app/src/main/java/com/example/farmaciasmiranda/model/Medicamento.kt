package com.example.farmaciasmiranda.model

import java.time.LocalDate

class Medicamento(id: Int, nombre: String, descripcion: String, precio: Double, categoria: Categoria,
                  val codigoRegistro: String, val laboratorio: String, val presentacion: String,
                  val requiereReceta: Boolean, val stockMinimo: Int) :
    Producto(id, nombre, descripcion, precio, categoria) {
    init { require(stockMinimo >= 0) }

    override fun obtenerInformacion(): String = "$nombre — $presentacion ($laboratorio)"
    fun requierePrescripcion(): Boolean = requiereReceta
    fun verificarDisponibilidad(cantidad: Int, stockActual: Int): Boolean = cantidad in 1..stockActual
    fun necesitaReposicion(stockActual: Int): Boolean = stockActual <= stockMinimo

    fun obtenerStockTotal(lotes: List<LoteMedicamento>, fecha: LocalDate = LocalDate.now()): Int =
        lotes.filter { it.medicamento.id == id && !it.estaVencido(fecha) && it.fechaFabricacion <= fecha }
            .sumOf { it.cantidadDisponible }
}

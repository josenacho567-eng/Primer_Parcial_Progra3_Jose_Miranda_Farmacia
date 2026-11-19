package com.example.farmaciasmiranda.model

import java.time.LocalDate

class Inventario(val id: Int, val nombreAlmacen: String, val ubicacion: String) {
    private val lotes = mutableListOf<LoteMedicamento>()

    fun agregarLote(lote: LoteMedicamento): Boolean = ingresarLotes(listOf(lote))

    internal fun ingresarLotes(nuevos: List<LoteMedicamento>): Boolean {
        val todos = lotes + nuevos
        if (todos.distinctBy { it.id }.size != todos.size ||
            todos.distinctBy { it.medicamento.id to it.numeroLote }.size != todos.size) return false
        if (todos.groupBy { it.medicamento.id }.values.any { grupo ->
            grupo.any { it.medicamento !== grupo.first().medicamento } ||
                grupo.sumOf { it.cantidadDisponible.toLong() } > Int.MAX_VALUE
        }) return false
        // El almacén conserva sus propias existencias, incluso si el llamador reutiliza un lote.
        lotes.addAll(nuevos.map { it.copia() })
        return true
    }

    private fun LoteMedicamento.copia(): LoteMedicamento = LoteMedicamento(id, numeroLote, medicamento,
        fechaFabricacion, fechaVencimiento, cantidadDisponible, precioCompra)

    fun consultarStock(medicamento: Medicamento): Int = medicamento.obtenerStockTotal(lotes)
    fun buscarMedicamento(id: Int): Medicamento? = lotes.find { it.medicamento.id == id }?.medicamento
    fun listarMedicamentos(): List<Medicamento> = lotes.map { it.medicamento }.distinctBy { it.id }
    fun obtenerMedicamentosStockBajo(): List<Medicamento> =
        listarMedicamentos().filter { it.necesitaReposicion(consultarStock(it)) }

    fun obtenerLotesPorVencer(): List<LoteMedicamento> =
        lotes.filter { it.cantidadDisponible > 0 && it.estaPorVencer(LocalDate.now(), 30) }.map { it.copia() }

    internal fun despachar(detalles: List<DetalleVenta>, fecha: LocalDate): Boolean {
        val plan = mutableMapOf<LoteMedicamento, Int>()
        for ((medicamento, lineas) in detalles.groupBy { it.medicamento }) {
            var pendiente = lineas.sumOf { it.cantidad.toLong() }
            val disponibles = lotes.filter { it.medicamento === medicamento &&
                it.fechaFabricacion <= fecha && !it.estaVencido(fecha) }.sortedBy { it.fechaVencimiento }
            for (lote in disponibles) {
                val unidades = minOf(pendiente, lote.cantidadDisponible.toLong()).toInt()
                if (unidades > 0) plan[lote] = unidades
                pendiente -= unidades
                if (pendiente == 0L) break
            }
            if (pendiente > 0) return false
        }
        plan.forEach { (lote, cantidad) -> lote.descontarCantidad(cantidad) }
        return true
    }
}

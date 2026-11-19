package com.example.farmaciasmiranda

import com.example.farmaciasmiranda.data.DatosFarmacia
import com.example.farmaciasmiranda.model.*
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class FarmaciaTest {
    private val hoy = LocalDate.now()
    private val farmacia = DatosFarmacia.crearFarmacia()
    private val inventario = farmacia.inventario
    private val medicamento = farmacia.buscarMedicamento(1)!!
    private val empleado = Empleado(20, "Eva", "Paz", "7001", "eva@example.com", "E20", "Auxiliar", 3200.0)
    private val proveedor = farmacia.listarProveedores().first()

    private fun detalle(id: Int, cantidad: Int) = DetalleCompra(id, medicamento, cantidad, 2.0,
        "P-$id", hoy.minusDays(20), hoy.plusDays(10))

    @Test fun polimorfismo() {
        val producto: Producto = medicamento
        assertTrue(producto.obtenerInformacion().contains(medicamento.laboratorio))
        val cliente = Cliente(20, "Elia", "Luz", "7002", "elia@example.com", "SIM", "Prado")
        val personas: List<Persona> = listOf(cliente, empleado)
        assertTrue(personas[0].obtenerInformacion().contains(cliente.documentoIdentidad))
        assertTrue(personas[1].obtenerInformacion().contains(empleado.cargo))
    }

    @Test fun catalogoYProveedores() {
        assertEquals(8, farmacia.listarMedicamentos().size)
        assertEquals(listOf(3, 3, 2, 0), farmacia.listarProveedores().map(farmacia::cantidadMedicamentos))
        assertEquals(32, inventario.consultarStock(medicamento))
        assertTrue(inventario.obtenerMedicamentosStockBajo().any { it.id == 2 })
        assertEquals(0, inventario.consultarStock(farmacia.buscarMedicamento(3)!!))
    }

    @Test fun ventaConservaPrecioYDescuentaUnaVez() {
        val linea = DetalleVenta(1, medicamento, 2)
        val venta = Venta(10, LocalDateTime.now(), null, empleado)
        assertTrue(venta.agregarDetalle(linea))
        assertTrue(medicamento.actualizarPrecio(10.0))
        assertTrue(venta.agregarDetalle(DetalleVenta(2, medicamento, 1)))
        assertEquals(11.0, linea.calcularSubtotal(), 0.001)
        assertEquals(21.0, venta.calcularTotal(), 0.001)
        assertTrue(venta.confirmarVenta(inventario))
        assertFalse(venta.confirmarVenta(inventario))
        assertFalse(venta.eliminarDetalle(1))
        assertFalse(venta.cancelarVenta())
        assertEquals(29, inventario.consultarStock(medicamento))
    }

    @Test fun faltanteNoProduceSalidaParcial() {
        val venta = Venta(11, LocalDateTime.now(), null, empleado)
        venta.agregarDetalle(DetalleVenta(1, medicamento, 32))
        venta.agregarDetalle(DetalleVenta(2, farmacia.buscarMedicamento(3)!!, 1))
        assertFalse(venta.confirmarVenta(inventario))
        assertEquals(32, inventario.consultarStock(medicamento))
        assertEquals(EstadoVenta.PENDIENTE, venta.estado)
    }

    @Test fun recepcionAtomicaYSinDuplicados() {
        val compra = Compra(10, hoy, proveedor)
        val linea = detalle(100, 3)
        compra.agregarDetalle(linea)
        compra.agregarDetalle(detalle(101, 2))
        assertEquals(6.0, linea.calcularSubtotal(), 0.001)
        assertEquals(10.0, compra.calcularTotal(), 0.001)
        assertTrue(farmacia.registrarCompra(compra))
        assertFalse(farmacia.registrarCompra(compra))
        assertFalse(compra.registrarIngreso(inventario))
        val conflicto = Compra(11, hoy, proveedor)
        conflicto.agregarDetalle(detalle(102, 4))
        conflicto.agregarDetalle(detalle(100, 1))
        assertFalse(farmacia.registrarCompra(conflicto))
        assertEquals(37, inventario.consultarStock(medicamento))
    }

    @Test fun limitesDeLote() {
        val lote = detalle(102, 2).generarLote()
        assertTrue(lote.estaPorVencer(hoy, 10))
        assertFalse(lote.estaVencido(lote.fechaVencimiento))
        assertTrue(lote.estaVencido(lote.fechaVencimiento.plusDays(1)))
        assertFalse(lote.descontarCantidad(3))
        assertFalse(lote.aumentarCantidad(-1))
        assertTrue(lote.descontarCantidad(2))
        assertEquals(0, lote.cantidadDisponible)
        assertThrows(IllegalArgumentException::class.java) { detalle(99, -1) }
        assertThrows(IllegalArgumentException::class.java) { DetalleVenta(2, medicamento, -1) }
    }
}

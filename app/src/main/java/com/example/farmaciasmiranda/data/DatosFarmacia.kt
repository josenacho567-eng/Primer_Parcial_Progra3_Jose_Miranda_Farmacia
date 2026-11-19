package com.example.farmaciasmiranda.data

import com.example.farmaciasmiranda.model.*
import java.time.LocalDate
import java.time.LocalDateTime

object DatosFarmacia {
    val farmacia = crearFarmacia()

    fun crearFarmacia(): Farmacia {
        val farmacia = Farmacia(1, "Farmacia Miranda", "Calle Prado 45", "22000100")
        val hoy = LocalDate.now()
        val proveedores = listOf(
            Proveedor(1, "Distribuidora Salud", "900001001", "22000101", "salud@example.com", "Calle Aroma 120, La Paz"),
            Proveedor(2, "Farma Bolivia", "900001002", "44000102", "farma@example.com", "Av. América 258, Cochabamba"),
            Proveedor(3, "Medicamentos del Sur", "900001003", "46000103", "sur@example.com", "Calle Junín 84, Sucre"),
            Proveedor(4, "Distribuidora Vida", "900001004", "33000104", "vida@example.com", "Av. Alemana 390, Santa Cruz")
        )
        proveedores.forEach { check(farmacia.agregarProveedor(it)) }
        val dolor = Categoria(1, "Analgésicos", "Productos para el dolor")
        val alergia = Categoria(2, "Antihistamínicos", "Productos para alergias")
        val digestivo = Categoria(3, "Gastrointestinales", "Productos del sistema digestivo")
        val antibiotico = Categoria(4, "Antibióticos", "Antibacterianos")
        val medicamentos = listOf(
            Medicamento(1, "Paracetamol", "Comprimidos de 500 mg", 5.5, dolor, "SIM-001",
                "Laboratorio Prado", "Caja de 10 unidades", false, 5),
            Medicamento(2, "Ibuprofeno", "Tabletas de 400 mg", 14.0, dolor, "SIM-002",
                "Laboratorio Andino", "Caja de 10 unidades", false, 5),
            Medicamento(3, "Loratadina", "Tabletas de 10 mg", 9.8, alergia, "SIM-003",
                "Laboratorio Prado", "Caja de 10 unidades", false, 5),
            Medicamento(4, "Omeprazol", "Cápsulas de 20 mg", 18.5, digestivo, "SIM-004",
                "Laboratorio Andino", "Caja de 10 unidades", false, 5),
            Medicamento(5, "Amoxicilina", "Cápsulas de 500 mg", 24.0, antibiotico, "SIM-005",
                "Laboratorio Prado", "Caja de 10 unidades", true, 5),
            Medicamento(6, "Cetirizina", "Tabletas de 10 mg", 12.0, alergia, "SIM-006",
                "Laboratorio Andino", "Caja de 10 unidades", false, 5),
            Medicamento(7, "Simeticona", "Gotas orales", 19.0, digestivo, "SIM-007",
                "Laboratorio Prado", "Frasco de 15 ml", false, 5),
            Medicamento(8, "Azitromicina", "Tabletas de 500 mg", 26.0, antibiotico, "SIM-008",
                "Laboratorio Andino", "Caja de 3 unidades", true, 5)
        )
        val ana = Empleado(1, "Ana", "Solís", "70000001", "ana@example.com", "E01", "Farmacéutica", 4800.0)
        val luis = Empleado(2, "Luis", "Rojas", "70000002", "luis@example.com", "E02", "Auxiliar", 3400.0)
        listOf(ana, luis).forEach { check(farmacia.agregarEmpleado(it)) }
        val clientes = listOf(
            Cliente(1, "Inés", "Luna", "70000003", "ines@example.com", "SIM101", "Prado 12"),
            Cliente(2, "José", "Vega", "70000004", "jose@example.com", "SIM102", "Junín 51"),
            Cliente(3, "María", "Soto", "70000005", "maria@example.com", "SIM103", "Aroma 70")
        )
        clientes.forEach { check(farmacia.registrarCliente(it)) }

        // Un detalle identifica un lote en todo el almacén.
        var siguienteLote = 1
        fun recibir(compra: Compra, medicamento: Medicamento, unidades: Int, diasHastaVencer: Long) {
            val id = siguienteLote++
            check(compra.agregarDetalle(DetalleCompra(id, medicamento, unidades, medicamento.precio * 0.6,
                "L-$id", hoy.minusMonths(6), hoy.plusDays(diasHastaVencer))))
        }
        val compras = proveedores.take(3).map { Compra(it.id, hoy.minusDays(10), it) }
        val unidades = listOf(32, 6, 10, 15, 5, 18, 9, 14)
        medicamentos.forEachIndexed { indice, medicamento ->
            recibir(compras[indice % 3], medicamento, unidades[indice], if (indice == 2) -1L else 180L)
        }
        recibir(compras.first(), medicamentos.first(), 3, 20)
        compras.forEach { check(farmacia.registrarCompra(it)) }

        val primera = Venta(1, LocalDateTime.now(), clientes[0], ana)
        check(primera.agregarDetalle(DetalleVenta(1, medicamentos[0], 3)))
        check(ana.registrarVenta(primera, farmacia))
        val segunda = Venta(2, LocalDateTime.now(), clientes[1], luis)
        check(segunda.agregarDetalle(DetalleVenta(1, medicamentos[1], 2)))
        check(luis.registrarVenta(segunda, farmacia))
        return farmacia
    }
}

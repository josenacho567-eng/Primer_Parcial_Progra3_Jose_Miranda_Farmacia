package com.example.farmaciasmiranda.model

class Farmacia(val id: Int, val nombre: String, val direccion: String, val telefono: String) {
    val inventario = Inventario(id, "Almacén de $nombre", direccion)
    private val proveedores = mutableListOf<Proveedor>()
    private val clientes = mutableListOf<Cliente>()
    private val empleados = mutableListOf<Empleado>()
    private val ventas = mutableListOf<Venta>()
    private val compras = mutableListOf<Compra>()

    fun agregarProveedor(proveedor: Proveedor): Boolean =
        proveedores.none { it.id == proveedor.id || it.nit == proveedor.nit } && proveedores.add(proveedor)
    fun registrarCliente(cliente: Cliente): Boolean =
        clientes.none { it.id == cliente.id } && clientes.add(cliente)
    fun agregarEmpleado(empleado: Empleado): Boolean =
        empleados.none { it.id == empleado.id } && empleados.add(empleado)
    fun buscarMedicamento(id: Int): Medicamento? = inventario.buscarMedicamento(id)
    fun listarMedicamentos(): List<Medicamento> = inventario.listarMedicamentos()
    fun listarProveedores(): List<Proveedor> = proveedores.toList()

    fun registrarVenta(venta: Venta): Boolean {
        if (venta.empleado !in empleados || (venta.cliente != null && venta.cliente !in clientes) ||
            ventas.any { it.id == venta.id } || !venta.confirmarVenta(inventario)) return false
        return ventas.add(venta)
    }
    fun registrarCompra(compra: Compra): Boolean {
        if (compra.proveedor !in proveedores || compras.any { it.id == compra.id } ||
            !compra.registrarIngreso(inventario)) return false
        return compras.add(compra)
    }
    fun cantidadMedicamentos(proveedor: Proveedor): Int = compras.filter { it.proveedor === proveedor }
        .flatMap { it.medicamentosSuministrados() }.distinctBy { it.id }.size

    fun obtenerResumenInventario(): String = "${listarMedicamentos().size} medicamentos · " +
        "${inventario.obtenerMedicamentosStockBajo().size} para reposición"
}

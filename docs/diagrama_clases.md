# Modelo de dominio de Farmacia Miranda

```mermaid
classDiagram
direction TB
class Categoria {
    +id: Int
    +nombre: String
    +descripcion: String
    +obtenerInformacion() String
    +actualizarDescripcion(nuevaDescripcion: String) Unit
}
class Producto {
    <<abstract>>
    +id: Int
    +nombre: String
    +descripcion: String
    +categoria: Categoria
    +precio: Double
    +obtenerPrecioFormateado() String
    +actualizarPrecio(nuevoPrecio: Double) Boolean
    +obtenerInformacion() String*
}
class Medicamento {
    +codigoRegistro: String
    +laboratorio: String
    +presentacion: String
    +requiereReceta: Boolean
    +stockMinimo: Int
    +obtenerInformacion() String
    +requierePrescripcion() Boolean
    +verificarDisponibilidad(cantidad: Int, stockActual: Int) Boolean
    +necesitaReposicion(stockActual: Int) Boolean
    +obtenerStockTotal(lotes: List~LoteMedicamento~, fecha: LocalDate) Int
}
class Inventario {
    +id: Int
    +nombreAlmacen: String
    +ubicacion: String
    -lotes: MutableList~LoteMedicamento~
    +agregarLote(lote: LoteMedicamento) Boolean
    ~ingresarLotes(nuevos: List~LoteMedicamento~) Boolean
    -LoteMedicamento.copia() LoteMedicamento
    +consultarStock(medicamento: Medicamento) Int
    +buscarMedicamento(id: Int) Medicamento?
    +listarMedicamentos() List~Medicamento~
    +obtenerMedicamentosStockBajo() List~Medicamento~
    +obtenerLotesPorVencer() List~LoteMedicamento~
    ~despachar(detalles: List~DetalleVenta~, fecha: LocalDate) Boolean
}
class LoteMedicamento {
    +id: Int
    +numeroLote: String
    +medicamento: Medicamento
    +fechaFabricacion: LocalDate
    +fechaVencimiento: LocalDate
    +precioCompra: Double
    +cantidadDisponible: Int
    +estaVencido(fechaActual: LocalDate) Boolean
    +estaPorVencer(fechaActual: LocalDate, dias: Long) Boolean
    +descontarCantidad(cantidad: Int) Boolean
    +aumentarCantidad(cantidad: Int) Boolean
    +obtenerInformacion() String
}
class Persona {
    <<abstract>>
    +id: Int
    +nombre: String
    +apellido: String
    +telefono: String
    +correo: String
    +obtenerNombreCompleto() String
    #contacto() String
    +obtenerInformacion() String*
}
class Cliente {
    +documentoIdentidad: String
    +direccion: String
    +obtenerInformacion() String
    +actualizarDireccion(nuevaDireccion: String) Unit
}
class Empleado {
    +codigoEmpleado: String
    +cargo: String
    +salario: Double
    +obtenerInformacion() String
    +registrarVenta(venta: Venta, farmacia: Farmacia) Boolean
}
class Venta {
    +id: Int
    +fecha: LocalDateTime
    +cliente: Cliente?
    +empleado: Empleado
    -detalles: MutableList~DetalleVenta~
    +estado: EstadoVenta
    +agregarDetalle(detalle: DetalleVenta) Boolean
    +eliminarDetalle(idDetalle: Int) Boolean
    +calcularTotal() Double
    +calcularCantidadProductos() Long
    +obtenerResumen() String
    +confirmarVenta(inventario: Inventario) Boolean
    +cancelarVenta() Boolean
}
class DetalleVenta {
    +id: Int
    +medicamento: Medicamento
    +cantidad: Int
    +precioUnitario: Double
    +calcularSubtotal() Double
    +obtenerInformacion() String
}
class EstadoVenta {
    <<enumeration>>
    PENDIENTE
    CONFIRMADA
    CANCELADA
}
class Proveedor {
    +id: Int
    +nombre: String
    +nit: String
    +direccion: String
    +telefono: String
    +correo: String
    +obtenerDatosContacto() String
    +obtenerInformacion() String
    +actualizarContacto(telefono: String, correo: String) Unit
}
class Compra {
    +id: Int
    +fecha: LocalDate
    +proveedor: Proveedor
    -detalles: MutableList~DetalleCompra~
    +estado: EstadoCompra
    +agregarDetalle(detalle: DetalleCompra) Boolean
    +calcularTotal() Double
    +obtenerResumen() String
    +medicamentosSuministrados() List~Medicamento~
    +registrarIngreso(inventario: Inventario) Boolean
    +cancelarCompra() Boolean
}
class DetalleCompra {
    +id: Int
    +medicamento: Medicamento
    +cantidad: Int
    +costoUnitario: Double
    +numeroLote: String
    +fechaFabricacion: LocalDate
    +fechaVencimiento: LocalDate
    +calcularSubtotal() Double
    +generarLote() LoteMedicamento
}
class EstadoCompra {
    <<enumeration>>
    PENDIENTE
    RECIBIDA
    CANCELADA
}
class Farmacia {
    +id: Int
    +nombre: String
    +direccion: String
    +telefono: String
    +inventario: Inventario
    -proveedores: MutableList~Proveedor~
    -clientes: MutableList~Cliente~
    -empleados: MutableList~Empleado~
    -ventas: MutableList~Venta~
    -compras: MutableList~Compra~
    +agregarProveedor(proveedor: Proveedor) Boolean
    +registrarCliente(cliente: Cliente) Boolean
    +agregarEmpleado(empleado: Empleado) Boolean
    +buscarMedicamento(id: Int) Medicamento?
    +listarMedicamentos() List~Medicamento~
    +listarProveedores() List~Proveedor~
    +registrarVenta(venta: Venta) Boolean
    +registrarCompra(compra: Compra) Boolean
    +cantidadMedicamentos(proveedor: Proveedor) Int
    +obtenerResumenInventario() String
}
Producto <|-- Medicamento
Persona <|-- Cliente
Persona <|-- Empleado
Producto "0..*" --> "1" Categoria : clasificación
Inventario "1" *-- "0..*" LoteMedicamento : existencias
LoteMedicamento "0..*" --> "1" Medicamento
Compra "0..*" --> "1" Proveedor
Compra "1" *-- "0..*" DetalleCompra
Venta "1" *-- "0..*" DetalleVenta
Venta "0..*" --> "0..1" Cliente
Venta "0..*" --> "1" Empleado
DetalleVenta "0..*" --> "1" Medicamento
DetalleCompra "0..*" --> "1" Medicamento
DetalleCompra ..> LoteMedicamento : genera
Venta ..> EstadoVenta
Compra ..> EstadoCompra
Farmacia "1" *-- "1" Inventario
Farmacia "0..*" o-- "0..*" Proveedor
Farmacia "0..*" o-- "0..*" Empleado
Farmacia "0..*" o-- "0..*" Cliente
Farmacia "0..1" --> "0..*" Compra : registra
Farmacia "0..1" --> "0..*" Venta : registra
```

Los detalles admiten multiplicidad 0..* mientras se prepara un documento y 1..* al confirmar una venta o recibir una compra. El inventario es propiedad de una farmacia y mantiene copias privadas de sus lotes. Los detalles representan líneas de valor de su documento.

La visibilidad sigue UML: + pública, − privada y # protegida. ~ representa el acceso interno del módulo Kotlin. En las propiedades con escritura restringida se muestra la visibilidad del getter. El asterisco al final de una operación Mermaid indica que es abstracta. Las firmas omiten los valores predeterminados de Kotlin.

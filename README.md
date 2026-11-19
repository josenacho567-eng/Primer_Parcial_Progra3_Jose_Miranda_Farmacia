# Farmacias Miranda

Aplicación académica de consulta en Kotlin, Jetpack Compose y Material 3. El catálogo responde cuánto queda disponible para vender. El directorio responde qué medicamentos ha suministrado cada proveedor.

## 1. Análisis del problema

Un medicamento puede llegar varias veces, con precios de compra y vencimientos diferentes. Por eso la existencia se registra por lote. Al recibir una compra, cada línea genera un lote. Al confirmar una venta, el inventario reúne las unidades necesarias empezando por los lotes que vencen antes. Las compras recibidas también son la fuente del historial de suministro.

## 2. Modelo de clases · Checkpoint 1

| Clase | Responsabilidad | Atributos principales | Métodos principales | Relaciones |
|---|---|---|---|---|
| Producto | Identidad y precio comercial | id, nombre, descripcion, precio | actualizarPrecio, obtenerPrecioFormateado | Tiene una categoría |
| Medicamento | Datos farmacéuticos y umbral de reposición | codigoRegistro, laboratorio, presentacion, requiereReceta, stockMinimo | obtenerStockTotal, necesitaReposicion | Especializa Producto |
| Categoria | Clasificar productos | id, nombre, descripcion | actualizarDescripcion | Compartida por productos |
| Proveedor | Identificar y contactar al distribuidor | id, nit, telefono, correo, direccion | actualizarContacto | Suministra mediante compras |
| Inventario | Controlar ingresos y salidas | nombreAlmacen, ubicacion, lotes | consultarStock, despachar, obtenerLotesPorVencer | Posee los lotes |
| LoteMedicamento | Conservar cantidad, costo y fechas de una partida | numeroLote, cantidadDisponible, precioCompra, fechaVencimiento | estaVencido, descontarCantidad | Corresponde a un medicamento |
| Persona | Representar identidad y contacto | nombre, apellido, telefono, correo | obtenerNombreCompleto, obtenerInformacion | Base de Cliente y Empleado |
| Cliente | Identificar al comprador | documentoIdentidad, direccion | actualizarDireccion | Participación opcional en ventas |
| Empleado | Identificar al responsable de la atención | codigoEmpleado, cargo, salario | registrarVenta | Atiende ventas |
| Venta | Confirmar o cancelar una transacción | fecha, cliente, empleado, detalles, estado | calcularTotal, confirmarVenta | Agrupa líneas de venta |
| DetalleVenta | Conservar cantidad y precio aplicado | medicamento, cantidad, precioUnitario | calcularSubtotal | Línea de una venta |
| Compra | Recibir productos del proveedor | fecha, proveedor, detalles, estado | registrarIngreso, calcularTotal | Agrupa líneas de compra |
| DetalleCompra | Describir la partida recibida | cantidad, costoUnitario, numeroLote, fechas | generarLote, calcularSubtotal | Origina una partida de medicamento |
| Farmacia | Coordinar los registros del establecimiento | inventario, clientes, empleados, proveedores, compras, ventas | registrarCompra, registrarVenta, cantidadMedicamentos | Administra el inventario y los registros |
| EstadoVenta | Restringir las transiciones de venta | PENDIENTE, CONFIRMADA, CANCELADA | — | Tipo de Venta.estado |
| EstadoCompra | Restringir las transiciones de compra | PENDIENTE, RECIBIDA, CANCELADA | — | Tipo de Compra.estado |

## 3. Diagramas UML

[Diagrama de clases en Mermaid](docs/diagrama_clases.md) · [PlantUML agrupado por seis áreas del negocio](docs/diagrama_clases.puml)

## 4. Conceptos de POO

- **Abstracción y herencia.** Producto define identidad, categoría, precio y la operación abstracta obtenerInformacion. Medicamento hereda esos miembros y añade los datos farmacéuticos.
- **Polimorfismo.** Una lista de Persona puede contener Cliente y Empleado. Al llamar a obtenerInformacion sobre cada persona se ejecuta la implementación correspondiente. La prueba polimorfismo comprueba ambos casos.
- **Encapsulamiento.** El precio y la cantidad disponible tienen setters privados. Los cambios pasan por operaciones que validan los valores. Compra y Venta protegen sus líneas y comprueban el estado antes de modificarlas.
- **Asociación.** Cada línea de venta referencia al medicamento y conserva una copia del precio unitario aplicado.
- **Agregación.** La farmacia registra objetos Proveedor y Empleado que se construyen independientemente.
- **Composición.** Farmacia crea su propio Inventario. Cada almacén posee copias privadas de sus lotes y entrega copias al consultar vencimientos. Las líneas inmutables representan valores dentro de sus respectivos documentos.

## 5. Implementación Kotlin · Checkpoint 2

Las clases y enumeraciones se encuentran en archivos individuales de `model/`. Sus constructores primarios reciben los datos obligatorios. `data/DatosFarmacia.kt` crea ocho medicamentos, cuatro categorías, cuatro proveedores, tres clientes y dos empleados. Registra tres compras y confirma dos ventas mediante las mismas operaciones del dominio que usan las pruebas. Las fechas de ejemplo son relativas al día de ejecución.

**Reglas del ejercicio**

- La disponibilidad excluye lotes vencidos y partidas con fabricación futura. El día de vencimiento es el último día disponible. La consulta de próximos vencimientos considera los siguientes 30 días y el día actual.
- El stock bajo incluye el umbral configurado para cada medicamento. Con cero unidades vendibles la interfaz muestra «Agotado». El catálogo mantiene los medicamentos cuyos lotes ya se agotaron o vencieron.
- El ingreso valida el conjunto completo antes de incorporarlo. Cada detalle de compra tiene un identificador único en el almacén, que también identifica el lote. El par medicamento y número de lote es único.
- Una venta se confirma en la fecha de atención. El despacho agrupa las líneas por medicamento y prepara un plan antes de descontar unidades. Una venta pendiente se puede cancelar. Los documentos recibidos o confirmados quedan cerrados.
- La simulación procesa operaciones secuenciales en memoria. Los importes usan Double según el enunciado. El desazucarado de bibliotecas permite usar java.time desde el mínimo del proyecto, API 24.

## 6. Interfaces · Checkpoints 3 y 4

MainActivity abre Medicamentos. Scaffold distribuye la TopAppBar y la NavigationBar. Cada pantalla usa LazyColumn con claves por identificador. Las tarjetas reutilizan Column, Row, Text y la tipografía de MaterialTheme.

**Medicamentos.** Cada tarjeta reúne nombre, descripción, categoría, laboratorio, presentación, indicación de receta, precio y existencias vendibles. Las etiquetas Disponible, Stock bajo y Agotado acompañan al color. Paracetamol queda con 32 unidades después de la primera venta. Loratadina aparece agotada porque su lote venció.

**Proveedores.** Cada tarjeta muestra nombre, NIT, teléfono, correo, dirección y cantidad de medicamentos distintos en compras recibidas. Dos partidas del mismo medicamento cuentan una sola vez. Distribuidora Vida figura con cero porque todavía no registra compras.

La barra inferior cambia de pantalla y conserva el desplazamiento de ambas listas. Desde Proveedores, Atrás devuelve al catálogo.

## 7. Ejecutar y comprobar

1. Abrir la carpeta FarmaciasMiranda en Android Studio y sincronizar Gradle con el SDK 37 instalado.
2. Seleccionar el módulo app y un dispositivo o emulador con Android API 24 o superior. Pulsar **Run**.
3. Recorrer el catálogo y abrir Proveedores. Los recuentos de los cuatro proveedores, en orden, son 3, 3, 2 y 0.
4. Desde PowerShell en la raíz, ejecutar `.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug` para probar el dominio y construir el APK. Las pruebas de `FarmaciaTest.kt` cubren polimorfismo, importes, relaciones, vencimientos, stock y rechazos sin modificaciones parciales.


# Prompts del proyecto Farmacias Miranda

Registro cronológico de las solicitudes utilizadas para desarrollar la aplicación y preparar su diagrama de clases. Los dos encargos extensos se presentan en versiones condensadas que se pueden volver a usar. Los mensajes breves se transcriben literalmente.

## 1. Implementar el catálogo inicial

**Versión condensada del prompt «Aplicación Android: Farmacia».**

Actúa como desarrollador Android especializado en Kotlin, POO, Jetpack Compose y Material Design 3. Trabaja directamente sobre el proyecto Android existente. Inspecciona primero su configuración y conserva su paquete, estructura y versiones de dependencias.

1. **Diseño de clases.** Modela Medicamento, Proveedor y Categoria. Utiliza Producto como clase abstracta para reunir id, nombre y precio, y demuestra polimorfismo con obtenerInformacion(). Cada medicamento pertenece a una categoría y tiene un proveedor. Documenta atributos, tipos, métodos, visibilidad, herencia y multiplicidades en Mermaid o PlantUML.
2. **Implementación.** Crea un archivo Kotlin por clase. Usa constructores, propiedades encapsuladas y nombres en español. Implementa estaDisponible(), necesitaReposicion() y obtenerPrecioFormateado(). Crea listas locales con al menos seis medicamentos y cuatro proveedores ficticios. Mantén las reglas del negocio fuera de la interfaz.
3. **Diseño de interfaz.** Crea dos pantallas con Compose y Material 3. La pantalla inicial debe mostrar «Farmacia» y «Catálogo de medicamentos», con tarjetas que incluyan nombre, descripción, categoría, precio en Bs, cantidad y estado: Disponible, Stock bajo o Agotado. La segunda debe mostrar «Proveedores» y «Distribuidores de medicamentos», con nombre, teléfono, correo, dirección y cantidad de medicamentos asociados.
4. **Funcionamiento y entrega.** Conecta ambas pantallas con navegación. Usa los objetos y sus métodos para alimentar las tarjetas. Calcula la cantidad de medicamentos de cada proveedor a partir de sus relaciones. Comprueba las dos listas, la navegación y la compilación de depuración. Documenta los cuatro checkpoints en README.md e informa qué validaciones ejecutaste.

Usa únicamente Kotlin y Compose para las interfaces. Excluye ventas, autenticación, bases de datos, Firebase y servicios externos de esta primera versión.

## 2. Entregar el primer diagrama editable

> peudes harcel el diagrama de clases de este proyecto y darmelo en un archivo .drawio

## 3. Ampliar el modelo de dominio

**Versión condensada del prompt «Modelo de clases completo para farmacia».**

Actúa como analista de sistemas, arquitecto de software y desarrollador Android. Inspecciona el proyecto y reutiliza las clases y pantallas existentes. Amplía el modelo para representar productos, proveedores, inventario, personas, ventas, compras, lotes y vencimientos. Mantén dos pantallas funcionales: Medicamentos y Proveedores.

| Área | Clases y responsabilidades solicitadas |
|---|---|
| Productos | Producto abstracto reúne identidad, descripción, categoría y precio. Medicamento añade registro, laboratorio, presentación, receta y stock mínimo. Categoria clasifica los productos. |
| Inventario | Inventario administra LoteMedicamento. Cada lote conserva número, medicamento, fabricación, vencimiento, cantidad disponible y precio de compra. Calcula el stock desde los lotes. |
| Personas | Persona abstracta reúne identidad y contacto. Cliente incorpora documento y dirección. Empleado incorpora código, cargo y salario, y atiende ventas. |
| Ventas | Venta tiene empleado, cliente opcional, fecha, estado y líneas DetalleVenta. Cada línea conserva cantidad y precio unitario aplicado. Usa EstadoVenta con PENDIENTE, CONFIRMADA y CANCELADA. |
| Compras | Compra pertenece a un Proveedor y contiene líneas DetalleCompra. Cada línea registra el costo y los datos necesarios para generar un lote. Usa EstadoCompra con PENDIENTE, RECIBIDA y CANCELADA. |
| Administración | Farmacia administra un inventario y registra proveedores, clientes, empleados, ventas y compras. |

- **UML.** Genera `docs/diagrama_clases.md` en Mermaid y `docs/diagrama_clases.puml` en PlantUML. Incluye todas las clases, enumeraciones, atributos, métodos, tipos, visibilidades y multiplicidades. Representa herencia, asociación, agregación y composición de acuerdo con el ciclo de vida de los objetos. Agrupa la versión PlantUML en las seis áreas del negocio.
- **Kotlin.** Implementa cada clase en su propio archivo. Usa constructores, encapsulamiento, herencia, métodos sobrescritos y colecciones tipadas. Valida precios, cantidades y fechas. Evita stock negativo, venta de lotes vencidos, confirmaciones sin existencias, doble ingreso de compras, cambios en ventas confirmadas y operaciones que dejen el inventario parcialmente modificado.
- **Pantallas.** Cada tarjeta de medicamento debe mostrar nombre, descripción, categoría, laboratorio, presentación, precio, stock calculado, estado e indicador de receta. Cada tarjeta de proveedor debe mostrar nombre, NIT, teléfono, correo, dirección y cantidad de medicamentos distintos suministrados según las compras registradas, incluido el caso de cero compras.
- **Objetos de ejemplo.** Crea ocho medicamentos, cuatro categorías, cuatro proveedores, dos empleados, tres clientes, dos ventas, tres compras y varios lotes con fechas y cantidades diferentes. Utiliza datos ficticios en memoria.
- **Pruebas.** Verifica herencia de productos y personas, subtotales y totales, consulta y descuento de existencias, stock bajo, vencimientos, rechazo de cantidades negativas y ventas sin stock, prevención de doble recepción y relaciones entre compras y proveedores. Ejecuta las pruebas y compila si el entorno lo permite.
- **Documentación.** Actualiza README.md con el análisis, una tabla de responsabilidades, atributos, métodos y relaciones, referencias a ambos diagramas, ejemplos de POO, explicación de las interfaces e instrucciones de ejecución. Comprueba la correspondencia entre UML, clases Kotlin, objetos de ejemplo y pantallas.

## 4. Corregir el error de la actividad principal

> hay un error en mainActivity

## 5. Exportar el modelo ampliado a draw.io

> puedes hacer el diagrama de clases en un archivo .drawio

## 6. Completar la vista principal del diagrama

> pero que este completa, con los atributos y los metodos

## 7. Guardar los prompts

> crea un md donde esten guardado los pront

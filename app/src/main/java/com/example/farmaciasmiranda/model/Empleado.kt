package com.example.farmaciasmiranda.model

class Empleado(id: Int, nombre: String, apellido: String, telefono: String, correo: String,
               val codigoEmpleado: String, val cargo: String, val salario: Double) :
    Persona(id, nombre, apellido, telefono, correo) {
    init { require(salario.isFinite() && salario >= 0) }

    override fun obtenerInformacion(): String = "${obtenerNombreCompleto()} · $cargo · ${contacto()}"
    fun registrarVenta(venta: Venta, farmacia: Farmacia): Boolean =
        venta.empleado === this && farmacia.registrarVenta(venta)
}

package com.example.farmaciasmiranda.model

abstract class Persona(val id: Int, val nombre: String, val apellido: String,
                       val telefono: String, val correo: String) {
    fun obtenerNombreCompleto(): String = "$nombre $apellido"
    protected fun contacto(): String = "$telefono · $correo"
    abstract fun obtenerInformacion(): String
}

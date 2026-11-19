package com.example.farmaciasmiranda.model

class Proveedor(val id: Int, val nombre: String, val nit: String, telefono: String,
                correo: String, val direccion: String) {
    var telefono: String = telefono
        private set
    var correo: String = correo
        private set

    fun obtenerDatosContacto(): String = "$telefono\n$correo"
    fun obtenerInformacion(): String = "$nombre\n${obtenerDatosContacto()}\n$direccion"
    fun actualizarContacto(telefono: String, correo: String) {
        require(telefono.isNotBlank() && correo.isNotBlank())
        this.telefono = telefono
        this.correo = correo
    }
}

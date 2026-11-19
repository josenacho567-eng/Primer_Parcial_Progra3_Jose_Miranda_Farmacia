package com.example.farmaciasmiranda.model

class Cliente(id: Int, nombre: String, apellido: String, telefono: String, correo: String,
              val documentoIdentidad: String, direccion: String) : Persona(id, nombre, apellido, telefono, correo) {
    var direccion: String = direccion
        private set

    override fun obtenerInformacion(): String = "${obtenerNombreCompleto()} · $documentoIdentidad · ${contacto()}"
    fun actualizarDireccion(nuevaDireccion: String) {
        require(nuevaDireccion.isNotBlank())
        direccion = nuevaDireccion
    }
}

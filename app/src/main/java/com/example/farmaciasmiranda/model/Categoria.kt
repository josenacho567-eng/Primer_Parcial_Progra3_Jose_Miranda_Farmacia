package com.example.farmaciasmiranda.model

class Categoria(val id: Int, val nombre: String, descripcion: String) {
    var descripcion: String = descripcion
        private set

    fun obtenerInformacion(): String = "$nombre — $descripcion"
    fun actualizarDescripcion(nuevaDescripcion: String) {
        require(nuevaDescripcion.isNotBlank())
        descripcion = nuevaDescripcion
    }
}

package com.example.farmaciasmiranda.model

import java.util.Locale

abstract class Producto(val id: Int, val nombre: String, val descripcion: String,
                        precio: Double, val categoria: Categoria) {
    var precio: Double = precio
        private set

    init { require(precio.isFinite() && precio >= 0) }

    abstract fun obtenerInformacion(): String

    fun obtenerPrecioFormateado(): String = String.format(Locale.forLanguageTag("es-BO"), "Bs %.2f", precio)

    fun actualizarPrecio(nuevoPrecio: Double): Boolean {
        if (!nuevoPrecio.isFinite() || nuevoPrecio < 0) return false
        precio = nuevoPrecio
        return true
    }
}

package com.example.greenps.View

class MapsFire {
    private var latitud = 0.0
    private var longitud = 0.0

    fun MapsFire() {}

    fun getLatitud(): Double {
        return latitud
    }

    fun setLatitud(latitud: Double) {
        this.latitud = latitud
    }

    fun getLongitud(): Double {
        return longitud
    }

    fun setLongitud(longitud: Double) {
        this.longitud = longitud
    }

}
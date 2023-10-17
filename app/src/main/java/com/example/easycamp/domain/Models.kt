package com.example.easycamp.domain

class Models {
    /**
     * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
     * objects that should be displayed on screen, or manipulated by the app.
     */
    data class Campamento(
        val nombre: String,
        val descripcion: String,
        val image: String
    )
}
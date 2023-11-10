package com.example.tema1practica2servicios

class PrimeEvent(message: String) {
    private var eventMessage: String = message

    fun getMessage(): String{
        return this.eventMessage
    }
}
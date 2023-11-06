package com.example.tema1practica2servicios

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.calcPrimosBoton).setOnClickListener{
            val serviceIntent = Intent(this, ARGPrimeIntentService::class.java)

            startService(serviceIntent)
            //val serviceIntent = Intent(this, ARGPrimeService::class.java)

            //serviceIntent.putExtra("inputExtra", "Calculando numeros primos en un servicio de primer plano")

            //ContextCompat.startForegroundService(this, serviceIntent)
        }

        findViewById<Button>(R.id.cambiarColorBoton).setOnClickListener{
            val color = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            it.setBackgroundColor(color)
        }
    }
}
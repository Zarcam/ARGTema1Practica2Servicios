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
    private val MAYOR_INT: Int = Integer.MAX_VALUE/40000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.calcPrimosBoton).setOnClickListener{
            /*var listaPrimos: ArrayList<Int> = ArrayList()
            listaPrimos.addAll(calcPrimesFrom(MAYOR_INT))

            listaPrimos.forEach {primo: Int ->
                Log.d("Primos", primo.toString())
            }*/
            val serviceIntent = Intent(this, ARGPrimeService::class.java)
            serviceIntent.putExtra("inputExtra", "Hola que tal")

            ContextCompat.startForegroundService(this, serviceIntent)
        }

        findViewById<Button>(R.id.cambiarColorBoton).setOnClickListener{
            val color = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            it.setBackgroundColor(color)
        }
    }

    private fun calcPrimesFrom(n: Int): List<Int>{
        var primes = ArrayList<Int>()

        for(i in 2..n){
            if(isPrime(i, i-1)){
                primes.add(i)
            }
        }
        return primes
    }

    private fun isPrime(n: Int, div: Int): Boolean{
        if(div == 1){
            return true
        }else if(n % div == 0){
            return false
        }else{
            return isPrime(n, div-1)
        }
    }
}
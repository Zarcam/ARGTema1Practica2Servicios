package com.example.tema1practica2servicios

import android.app.IntentService
import android.content.Intent
import android.util.Log

class ARGPrimeIntentService : IntentService("ARGPrimeIntentService") {
    private val MAYOR_INT: Int = Integer.MAX_VALUE/175000

    override fun onHandleIntent(intent: Intent?) {
        Thread{
            calcPrimesFrom(MAYOR_INT).forEach{
                Log.d("Primos", it.toString())
            }
        }.start()
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
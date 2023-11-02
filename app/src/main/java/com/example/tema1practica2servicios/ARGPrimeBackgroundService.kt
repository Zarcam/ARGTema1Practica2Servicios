package com.example.tema1practica2servicios

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ARGPrimeBackgroundService : Service() {
    private val MAYOR_INT: Int = Integer.MAX_VALUE/175000


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        this.run()
        return START_NOT_STICKY
    }

    private fun run(){
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

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
package com.example.tema1practica2servicios

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class ARGPrimeWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    private val MAYOR_INT = Integer.MAX_VALUE/275000;

    override fun doWork(): Result {
        return Result.success(workDataOf(Pair("Primos", calcPrimesFrom(MAYOR_INT))))
    }


    private fun calcPrimesFrom(n: Int): IntArray{
        var primes = ArrayList<Int>()

        for(i in 2..n){
            if(isPrime(i, i-1)){
                primes.add(i)
            }
        }
        return primes.toIntArray()
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
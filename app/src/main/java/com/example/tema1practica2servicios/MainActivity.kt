package com.example.tema1practica2servicios

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.calcPrimosBoton).setOnClickListener{
            val workManager: WorkManager = WorkManager.getInstance(this)
            val peticion: WorkRequest = OneTimeWorkRequestBuilder<ARGPrimeWorker>().build()
            val id = peticion.id
            workManager.getWorkInfoByIdLiveData(id).observe(this, Observer { info ->
                if(info != null && info.state.isFinished){
                    val resultado = info.outputData.getIntArray("Primos")
                    resultado?.forEach {
                        Log.d("Primos", it.toString())
                    }
                }
            })
            workManager.enqueue(peticion)

            //val serviceIntent = Intent(this, ARGPrimeIntentService::class.java)
            //startService(serviceIntent)

            //val serviceIntent = Intent(this, ARGPrimeBackgroundService::class.java)
            //startService(serviceIntent)

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
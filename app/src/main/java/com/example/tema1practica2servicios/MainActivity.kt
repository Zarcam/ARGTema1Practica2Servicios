package com.example.tema1practica2servicios

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.Telephony
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.w3c.dom.Text
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var receiver: BroadcastReceiver
    private val READ_CALL_LOG_REQUEST_CODE = 160

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        receiver = object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val textoBat = findViewById<TextView>(R.id.textoBateria)
                when(intent?.action){
                    Intent.ACTION_AIRPLANE_MODE_CHANGED -> textoBat.text = "Modo avion activado"
                    Intent.ACTION_BATTERY_LOW -> textoBat.text = "Bateria baja"
                }
            }
        }
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_BATTERY_LOW)
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        ContextCompat.registerReceiver(this, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)

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

            EventBus.getDefault().register(this)

            //INTENT SERVICE
            //val serviceIntent = Intent(this, ARGPrimeIntentService::class.java)
            //startService(serviceIntent)

            //BACKGROUND SERVICE
            //val serviceIntent = Intent(this, ARGPrimeBackgroundService::class.java)
            //startService(serviceIntent)

            //FOREGROUND SERVICE
            //val serviceIntent = Intent(this, ARGPrimeService::class.java)
            //serviceIntent.putExtra("inputExtra", "Calculando numeros primos en un servicio de primer plano")
            //ContextCompat.startForegroundService(this, serviceIntent)
        }

        findViewById<Button>(R.id.cambiarColorBoton).setOnClickListener{
            val color = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            it.setBackgroundColor(color)
        }

        findViewById<Button>(R.id.verLlamadasButton).setOnClickListener{
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED){
                readCalls()
            }else{
                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_CALL_LOG), READ_CALL_LOG_REQUEST_CODE)
            }
        }
    }

    private fun readCalls(){
        val cr = applicationContext.contentResolver

        val cursor = cr.query(CallLog.Calls.CONTENT_URI, null, null, null)
        if(cursor != null){
            if(cursor.moveToFirst()) {
                 do{
                    val numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER)
                    val number = cursor.getString(numberIndex)
                    Log.d("Ultimas llamadas", number)
                }while (cursor.moveToNext())
            }
        }
        cursor?.close()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: PrimeEvent){
        Log.d("PrimeEvent", event.getMessage())
    }
}
package com.example.tema1practica2servicios

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class ARGPrimeService : Service() {
    private val CHANNEL_ID = "ForegroundPrimeServiceChannel"
    private lateinit var manager: NotificationManager
    private var enable: Boolean = false
    override fun onCreate() {
        super.onCreate()
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Prime Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
            manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Servicio calculando primos en primer plano")
            .setContentText(input)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(102, notification)
        this.run()
        return START_NOT_STICKY
    }

    private fun run() {
        enable = true
        Thread{
            while(enable){
                Log.e("Hola", "Running", )
                try{
                    Thread.sleep(2000)
                }catch (ex: InterruptedException){
                    ex.printStackTrace()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        enable = false
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
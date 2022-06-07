package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*

class MyService : Service() {

    inner class MyServiceBinder : Binder() {
        fun getService(): MyService = this@MyService
    }
    private val binder: Binder = MyServiceBinder()


    override fun onBind(intent: Intent): IBinder {
        Log.d("Service","onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("Service","onUnbind")
        return super.onUnbind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service","onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onRebind(intent: Intent?) {
        Log.d("Service","onRebind")
        super.onRebind(intent)
    }

    override fun onCreate() {
        Log.d("Service","onCreate")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d("Service","onDestroy")
        super.onDestroy()
    }

    private val mGenerator = Random()
    fun getNumber(): Int {
        Log.d("Service","getNumber")
        return mGenerator.nextInt(100)
    }
}


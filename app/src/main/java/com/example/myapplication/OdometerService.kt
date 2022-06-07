package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*

class OdometerService : Service() {

    override fun onBind(intent: Intent): IBinder {
        Log.d("Service","onBind")
        return binder
    }

    private val binder: IBinder = OdometerBinder()
    inner class OdometerBinder : Binder() {
        fun getOdometer(): OdometerService = this@OdometerService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        Log.d("Service","onStartCommand")
    }

    private val mGenerator = Random()
    fun getDistance(): Int {
        return mGenerator.nextInt(100)
    }
}


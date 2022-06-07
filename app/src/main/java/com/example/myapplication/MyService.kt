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

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG,"onBind")
        val myServicebinder: MyServiceBinder = MyServiceBinder()
        return myServicebinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG,"onUnbind")
        return super.onUnbind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,"onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG,"onRebind")
        super.onRebind(intent)
    }

    override fun onCreate() {
        Log.d(TAG,"onCreate")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d(TAG,"onDestroy")
        super.onDestroy()
    }

    private val mGenerator = Random()
    fun getNumber(): Int {
        Log.d(TAG,"getNumber")
        return mGenerator.nextInt(100)
    }
}


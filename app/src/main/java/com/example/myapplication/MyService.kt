package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*

class MyService : Service() {

    var counter:Int = 0

    inner class MyServiceBinder : Binder() {
        fun getService(): MyService = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG,"Service: onBind")
        val myServicebinder: MyServiceBinder = MyServiceBinder()
        return myServicebinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG,"Service: onUnbind")
        return super.onUnbind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,"Service: onStartCommand")
        Thread{loop()}.start()
        //return super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG,"Service: onRebind")
        super.onRebind(intent)
    }

    override fun onCreate() {
        Log.d(TAG,"Service: onCreate")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d(TAG,"Service: onDestroy")
        super.onDestroy()
    }

    fun loop(){
        counter = 0
        while(true) {
            counter++
            Log.d(TAG,"Thread: $counter")
            Thread.sleep(1000)
        }
    }

    private val mGenerator = Random()
    fun getRndNumber(): Int {
        Log.d(TAG,"getRndNumber")
        return mGenerator.nextInt(100)
    }

    fun getNumber():Int{
        Log.d(TAG,"getNumber = $counter")
        return counter
    }
}

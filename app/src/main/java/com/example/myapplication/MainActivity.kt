package com.example.myapplication

import android.annotation.TargetApi
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

val TAG:String = "TestingService"

class MainActivity : AppCompatActivity() {

    private var myService: MyService? = null

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            myService = (p1 as MyService.MyServiceBinder).getService()
            Log.d(TAG, "onService Connected")
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(TAG, "onService Disconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv: TextView = findViewById(R.id.textView)
        //This is the intent used for creating the service
        intent = Intent(this, MyService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "Build verison is greater than O")
        }


        findViewById<Button>(R.id.buttonGet).setOnClickListener {
            if (isMyServiceRunning(MyService::class.java)) {
                val num = myService?.getNumber()
                tv.text = "Running: $num"
            } else {
                tv.text = "Not Running"
            }
        }

        //Bind
        findViewById<Button>(R.id.buttonStartBound).setOnClickListener {
            if (!isMyServiceRunning(MyService::class.java)) {
//                Intent(this, MyService::class.java).also {intent ->
//                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)}
                Log.d(TAG, "Service was not running. Establishing bind.")
                bindService(intent, serviceConnection, BIND_AUTO_CREATE)
            } else {
                Log.d(TAG, "Service was already running. Establishing bind.")
                bindService(intent, serviceConnection, BIND_AUTO_CREATE)
            }
        }

        //Unbind
        findViewById<Button>(R.id.buttonStopBound).setOnClickListener {
            if (isMyServiceRunning(MyService::class.java)) {
                unbindService(serviceConnection)
                Log.d(TAG, "Service was running. Unbinding.")
                //bound = false
            } else {
                Log.d(TAG, "Service was not running. Cannot unbind.")
            }
        }

        //Start Service
        findViewById<Button>(R.id.buttonStartService).setOnClickListener {
            if (!isMyServiceRunning(MyService::class.java)) {
                startService(intent)
                Log.d(TAG, "Service was not running. Starting service.")
            } else {
                Log.d(TAG, "Service was already running. No need to start again.")
            }
        }

        //Stop Service
        findViewById<Button>(R.id.buttonStopService).setOnClickListener {
            if (isMyServiceRunning(MyService::class.java)) {
                stopService(intent)
                Log.d(TAG, "Service was running. Stopping service.")
            } else {
                Log.d(TAG, "Service was not running. Nothing to stop.")
            }
        }
    }


    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        var manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name.equals(service.service.className)) {
                return true
            }
        }
        return false
    }
}
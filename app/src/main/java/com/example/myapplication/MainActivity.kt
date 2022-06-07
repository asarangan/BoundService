package com.example.myapplication

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var myService: MyService
    //var bound: Boolean = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val myServiceBinder: MyService.MyServiceBinder =
                p1 as MyService.MyServiceBinder
            myService = myServiceBinder.getService()
            Log.d("Service","Service Connection")
            //bound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d("Service","Service Disconnection")
            //bound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv: TextView = findViewById(R.id.textView)
        intent = Intent(this, MyService::class.java)

        findViewById<Button>(R.id.buttonGet).setOnClickListener {
            if (isMyServiceRunning(MyService::class.java)) {
                val num = myService.getNumber()
                tv.text = "Running: $num"
            } else {
                tv.text = "Not Running"
            }
        }

        findViewById<Button>(R.id.buttonStopBound).setOnClickListener {
            if (isMyServiceRunning(MyService::class.java)){
                unbindService(serviceConnection)
                //bound = false
            }
        }

        findViewById<Button>(R.id.buttonStartBound).setOnClickListener {
            if (!isMyServiceRunning(MyService::class.java)){
//                Intent(this, MyService::class.java).also {intent ->
//                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)}
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            }
        }

        findViewById<Button>(R.id.buttonStartService).setOnClickListener {
            if (!isMyServiceRunning(MyService::class.java)){
                intent = Intent(this, MyService::class.java)
                startService(intent)
            }
        }

        findViewById<Button>(R.id.buttonStopService).setOnClickListener {
            if (isMyServiceRunning(MyService::class.java)){
                stopService(intent)
            }
        }
    }


//    override fun onStart() {
//        super.onStart()
//        // Bind to LocalService
//        Intent(this, OdometerService::class.java).also { intent ->
//            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
//        }
//    }

//    override fun onStop() {
//        super.onStop()
//        unbindService(serviceConnection)
//    }


    private fun isMyServiceRunning(serviceClass : Class<*> ) : Boolean{
        var manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name.equals(service.service.className)) {
                return true
            }
        }
        return false
    }

}
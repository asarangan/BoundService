package com.example.myapplication

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var odometer: OdometerService
    var bound: Boolean = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val odometerBinder: OdometerService.OdometerBinder =
                p1 as OdometerService.OdometerBinder
            odometer = odometerBinder.getOdometer()
            bound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            bound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonGet: Button = findViewById(R.id.buttonGet)
        val buttonStart: Button = findViewById(R.id.buttonStart)
        val buttonStop: Button = findViewById(R.id.buttonStop)
        val tv: TextView = findViewById(R.id.textView)

        buttonGet.setOnClickListener {
            if (isMyServiceRunning(OdometerService::class.java)) {
                val num = odometer.getDistance()
                tv.text = "Running: $num"
            } else {
                tv.text = "Not Running"
            }
        }

        buttonStop.setOnClickListener {
            if (isMyServiceRunning(OdometerService::class.java)){
                unbindService(serviceConnection)
                //bound = false
            }
        }

        buttonStart.setOnClickListener {
            if (!isMyServiceRunning(OdometerService::class.java)){
                Intent(this, OdometerService::class.java).also { intent ->
                    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
                }
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
package com.example.locationupdate

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat


class LocationUpdateService: Service() {

    lateinit var locationManeget:LocationManager

    private lateinit var locationListener:LocationListener

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        locationManeget = getSystemService(LOCATION_SERVICE) as LocationManager


        locationListener = object : LocationListener{
            override fun onLocationChanged(location: Location) {

                Toast.makeText(this@LocationUpdateService, "${location.latitude.toString()},${location.longitude.toString()}", Toast.LENGTH_SHORT).show()
                Log.d("onLocationChangedservice", "${location.latitude.toString()},${location.longitude.toString()} ")
            }

        }

        if (checkPermission()) {
            locationManeget.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000,
                0f,
                locationListener
            )
        }else{

        }


    }


    fun checkPermission():Boolean{

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }else{
            return false
        }

    }




    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val intent = Intent(this,MainActivity::class.java)
        val paddingIntent = PendingIntent.getActivity(this,10,intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationManager :NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationChannel("LocationUPdate","Location",NotificationManager.IMPORTANCE_HIGH))
        }


        val notification = NotificationCompat.Builder(this,"LocationUPdate")
            .setContentTitle("LocationUpdateing..")
            .setContentText("Location Update Working..")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(paddingIntent)
            .build()

        startForeground(10,notification)


        return START_STICKY
    }



}
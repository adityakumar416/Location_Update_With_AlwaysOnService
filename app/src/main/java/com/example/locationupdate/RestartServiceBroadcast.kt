package com.example.locationupdate

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES

class RestartServiceBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {

            val intents = Intent(context, LocationUpdateService::class.java)
            if (VERSION.SDK_INT >= VERSION_CODES.O) {
                context?.startForegroundService(intents)
            } else {
                context?.startService(intents)

            }

        }


    }
}
package com.example.saferoute

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.os.IBinder
import android.telephony.SmsManager

class LiveLocationService : Service() {

    private lateinit var locationHelper: LocationHelper
    private lateinit var prefs: SharedPreferences
    private var isSending = false

    override fun onCreate() {
        super.onCreate()
        locationHelper = LocationHelper(this)
        prefs = getSharedPreferences("contacts", MODE_PRIVATE)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isSending) {
            isSending = true
            locationHelper.requestLocationUpdates { location ->
                sendLocationSms(location)
            }
        }
        return START_STICKY
    }

    private fun sendLocationSms(location: Location) {
        val yourName = prefs.getString("yourName", "")
        val message = "SOS! I am in danger. This is my live location: http://maps.google.com/maps?q=${location.latitude},${location.longitude}"

        val contacts = getTrustedContacts()
        val smsManager = SmsManager.getDefault()
        contacts.forEach { phone ->
            smsManager.sendTextMessage(phone, null, message, null, null)
        }
    }

    private fun getTrustedContacts(): List<String> {
        val contacts = mutableListOf<String>()
        for (i in 1..3) {
            val phone = prefs.getString("c${i}_phone", null)
            if (phone != null && phone.isNotEmpty()) {
                contacts.add(phone)
            }
        }
        return contacts
    }

    override fun onDestroy() {
        super.onDestroy()
        isSending = false
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
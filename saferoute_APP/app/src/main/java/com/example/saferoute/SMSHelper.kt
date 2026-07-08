package com.example.saferoute

import android.content.Context
import android.os.Build
import android.telephony.SmsManager
import android.util.Log

class SMSHelper(private val context: Context) {

    fun sendSMS(phone: String, message: String): Boolean {
        return try {
            val smsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                context.getSystemService(SmsManager::class.java)
            } else {
                @Suppress("DEPRECATION")
                SmsManager.getDefault()
            }
            smsManager.sendTextMessage(phone, null, message, null, null)
            true
        } catch (e: Exception) {
            Log.e("SMSHelper", "Failed to send SMS to $phone", e)
            false
        }
    }
}
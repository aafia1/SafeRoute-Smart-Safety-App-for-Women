package com.example.saferoute

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.net.URLEncoder

class SOSService : Service() {

    private lateinit var prefs: SharedPreferences

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotification()
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "SOS Service Started", Toast.LENGTH_SHORT).show()
        }

        prefs = getSharedPreferences("contacts", MODE_PRIVATE)
        val locationHelper = LocationHelper(this)

        locationHelper.getCurrentLocation { location ->
            if (location == null) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this, "Could not get location. Cannot send SOS.", Toast.LENGTH_LONG).show()
                }
                stopSelf()
                return@getCurrentLocation
            }

            val yourName = prefs.getString("yourName", "Someone")
            val message = "🚨 SOS Alert! $yourName needs help. My location: https://maps.google.com/?q=${location.latitude},${location.longitude}"

            val contacts = listOf(
                prefs.getString("c1_phone", ""),
                prefs.getString("c2_phone", ""),
                prefs.getString("c3_phone", "")
            ).filter { it?.isNotEmpty() == true }

            if (contacts.isEmpty()) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this, "No trusted contacts found. Please add contacts in settings.", Toast.LENGTH_LONG).show()
                }
                stopSelf()
                return@getCurrentLocation
            }

            // Send SMS
            val smsHelper = SMSHelper(this)
            var sentSmsCount = 0
            contacts.forEach { phone ->
                if (smsHelper.sendSMS(phone!!, message)) {
                    sentSmsCount++
                }
            }

            // Send WhatsApp messages
            contacts.forEach { phone ->
                sendWhatsAppMessage(phone!!, message)
            }

            val confirmationMessage = if (sentSmsCount > 0) {
                "SOS sent to $sentSmsCount of ${contacts.size} contacts via SMS."
            } else {
                "Failed to send SOS via SMS. Please check permissions and credits."
            }

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(this, confirmationMessage, Toast.LENGTH_LONG).show()
            }
            stopSelf()
        }

        return START_NOT_STICKY
    }

    private fun sendWhatsAppMessage(phone: String, message: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            val urlEncodedMessage = URLEncoder.encode(message, "UTF-8")
            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=$urlEncodedMessage")
            intent.setPackage("com.whatsapp")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(this, "WhatsApp not installed or invalid phone number for WhatsApp.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createNotification() {
        val channelId = "safety_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "SafeRoute", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("SafeRoute Active")
            .setContentText("Your safety mode is ON")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
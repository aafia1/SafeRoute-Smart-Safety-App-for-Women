package com.example.saferoute

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.saferoute.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val permissionCode = 100
    private var isSirenOn = false
    private var isLiveLocationOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase Realtime Database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d("FirebaseData", "Value is: " + value)
                Toast.makeText(this@MainActivity, "Database Updated", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("FirebaseData", "Failed to read value.", error.toException())
            }
        })

        requestAllPermissions()

        binding.infoButton.setOnClickListener {
            showInfoSlider()
        }

        binding.sosButton.setOnClickListener {
            if (isLiveLocationOn) {
                stopService(Intent(this, LiveLocationService::class.java))
                binding.sosButton.text = "SOS"
                Toast.makeText(this, "Live Location Sharing Deactivated", Toast.LENGTH_SHORT).show()
            } else {
                startService(Intent(this, LiveLocationService::class.java))
                binding.sosButton.text = "Stop SOS"
                Toast.makeText(this, "Live Location Sharing Activated", Toast.LENGTH_SHORT).show()
            }
            isLiveLocationOn = !isLiveLocationOn
        }

        binding.shareLocationButton.setOnClickListener {
            startService(Intent(this, SOSService::class.java))
        }

        binding.fakeCallButton.setOnClickListener {
            startActivity(Intent(this, FakeCallActivity::class.java))
        }

        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, TrustedContactsActivity::class.java))
        }

        binding.sirenButton.setOnClickListener {
            if (isSirenOn) {
                stopService(Intent(this, SirenService::class.java))
                binding.sirenButton.text = "Siren"
                Toast.makeText(this, "Siren Deactivated", Toast.LENGTH_SHORT).show()
            } else {
                startService(Intent(this, SirenService::class.java))
                binding.sirenButton.text = "Stop Siren"
                Toast.makeText(this, "Siren Activated", Toast.LENGTH_SHORT).show()
            }
            isSirenOn = !isSirenOn
        }
    }

    private fun showInfoSlider() {
        val dialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.info_slider, null)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun requestAllPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            permissions.add(Manifest.permission.FOREGROUND_SERVICE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC)
        }

        ActivityCompat.requestPermissions(
            this,
            permissions.toTypedArray(),
            permissionCode
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionCode) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                 Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissions are required for the app to function.", Toast.LENGTH_LONG).show()
            }
        }
    }
}

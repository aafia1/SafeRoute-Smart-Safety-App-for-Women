package com.example.saferoute

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.saferoute.databinding.ActivityTrustedContactsBinding

class TrustedContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrustedContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrustedContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.yourInfoBtn.setOnClickListener {
            startActivity(Intent(this, YourInformationActivity::class.java))
        }

        binding.emergencyContactsBtn.setOnClickListener {
            startActivity(Intent(this, EmergencyContactsActivity::class.java))
        }

        binding.fakeCallerBtn.setOnClickListener {
            startActivity(Intent(this, FakeCallerSettingsActivity::class.java))
        }
    }
}
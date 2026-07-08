package com.example.saferoute

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.saferoute.databinding.ActivityEmergencyContactsBinding

class EmergencyContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmergencyContactsBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("contacts", MODE_PRIVATE)

        loadContacts()

        binding.saveButton.setOnClickListener {
            saveContacts()
        }
    }

    private fun loadContacts() {
        binding.c1Name.setText(prefs.getString("c1_name", ""))
        binding.c1Phone.setText(prefs.getString("c1_phone", ""))
        binding.c2Name.setText(prefs.getString("c2_name", ""))
        binding.c2Phone.setText(prefs.getString("c2_phone", ""))
        binding.c3Name.setText(prefs.getString("c3_name", ""))
        binding.c3Phone.setText(prefs.getString("c3_phone", ""))
    }

    private fun saveContacts() {
        prefs.edit().apply {
            putString("c1_name", binding.c1Name.text.toString())
            putString("c1_phone", binding.c1Phone.text.toString())
            putString("c2_name", binding.c2Name.text.toString())
            putString("c2_phone", binding.c2Phone.text.toString())
            putString("c3_name", binding.c3Name.text.toString())
            putString("c3_phone", binding.c3Phone.text.toString())
            apply()
        }
    }
}
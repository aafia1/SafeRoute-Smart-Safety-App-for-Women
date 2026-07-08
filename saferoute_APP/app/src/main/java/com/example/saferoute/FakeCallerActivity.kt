package com.example.saferoute

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.saferoute.databinding.ActivityFakeCallerBinding

class FakeCallerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFakeCallerBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFakeCallerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("fake_caller", MODE_PRIVATE)

        loadFakeCallerNames()
    }

    override fun onPause() {
        super.onPause()
        saveFakeCallerNames()
    }

    private fun loadFakeCallerNames() {
        binding.fakeCallerName1.setText(prefs.getString("fakeCallerName1", ""))
        binding.fakeCallerName2.setText(prefs.getString("fakeCallerName2", ""))
        binding.fakeCallerName3.setText(prefs.getString("fakeCallerName3", ""))
    }

    private fun saveFakeCallerNames() {
        prefs.edit().apply {
            putString("fakeCallerName1", binding.fakeCallerName1.text.toString())
            putString("fakeCallerName2", binding.fakeCallerName2.text.toString())
            putString("fakeCallerName3", binding.fakeCallerName3.text.toString())
            apply()
        }
    }
}
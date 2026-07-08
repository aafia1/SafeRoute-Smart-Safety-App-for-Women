package com.example.saferoute

import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saferoute.databinding.ActivityFakeCallBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FakeCallActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFakeCallBinding
    private lateinit var ringtone: Ringtone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFakeCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = FirebaseDatabase.getInstance().getReference("Users")
        database.child("fake_caller_info").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val callers = mutableListOf<FakeCallerInfo>()
                for (callerSnapshot in snapshot.children) {
                    val caller = callerSnapshot.getValue(FakeCallerInfo::class.java)
                    caller?.let { callers.add(it) }
                }

                if (callers.isNotEmpty()) {
                    val randomCaller = callers.random()
                    binding.callerName.text = randomCaller.name
                    binding.callerNumber.text = randomCaller.number
                } else {
                    binding.callerName.text = "Unknown Caller"
                    binding.callerNumber.text = ""
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FakeCallActivity, "Failed to load caller information.", Toast.LENGTH_SHORT).show()
                binding.callerName.text = "Unknown Caller"
                binding.callerNumber.text = ""
            }
        })

        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        ringtone = RingtoneManager.getRingtone(applicationContext, notification)
        ringtone.play()

        binding.rejectCallButton.setOnClickListener {
            finish()
        }

        binding.acceptCallButton.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::ringtone.isInitialized && ringtone.isPlaying) {
            ringtone.stop()
        }
    }
}
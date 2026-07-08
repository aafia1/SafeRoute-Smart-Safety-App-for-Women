package com.example.saferoute

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saferoute.databinding.ActivityFakeCallerSettingsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FakeCallerSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFakeCallerSettingsBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFakeCallerSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("Users")

        loadFakeCallerInfo()

        binding.saveButton.setOnClickListener {
            saveFakeCallerInfo()
        }
    }

    private fun loadFakeCallerInfo() {
        database.child("fake_caller_info").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val callers = mutableListOf<FakeCallerInfo>()
                    if (snapshot.exists()) {
                        for (callerSnapshot in snapshot.children) {
                            val caller = callerSnapshot.getValue(FakeCallerInfo::class.java)
                            caller?.let { callers.add(it) }
                        }
                    }

                    // Clear fields before setting them
                    binding.fakeCallerName1.setText("")
                    binding.fakeCallerNumber1.setText("")
                    binding.fakeCallerName2.setText("")
                    binding.fakeCallerNumber2.setText("")
                    binding.fakeCallerName3.setText("")
                    binding.fakeCallerNumber3.setText("")

                    if (callers.size > 0) {
                        binding.fakeCallerName1.setText(callers[0].name)
                        binding.fakeCallerNumber1.setText(callers[0].number)
                    }
                    if (callers.size > 1) {
                        binding.fakeCallerName2.setText(callers[1].name)
                        binding.fakeCallerNumber2.setText(callers[1].number)
                    }
                    if (callers.size > 2) {
                        binding.fakeCallerName3.setText(callers[2].name)
                        binding.fakeCallerNumber3.setText(callers[2].number)
                    }
                } catch (e: DatabaseException) {
                    // This will happen if the data is in the old, single-object format.
                    // We will delete the corrupt data and notify the user.
                    Toast.makeText(this@FakeCallerSettingsActivity, "Corrupt data found. Clearing...", Toast.LENGTH_LONG).show()
                    snapshot.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FakeCallerSettingsActivity, "Failed to load fake caller information.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveFakeCallerInfo() {
        val callers = mutableListOf<FakeCallerInfo>()

        val name1 = binding.fakeCallerName1.text.toString()
        val number1 = binding.fakeCallerNumber1.text.toString()
        if (name1.isNotBlank() || number1.isNotBlank()) {
            callers.add(FakeCallerInfo(name1, number1))
        }

        val name2 = binding.fakeCallerName2.text.toString()
        val number2 = binding.fakeCallerNumber2.text.toString()
        if (name2.isNotBlank() || number2.isNotBlank()) {
            callers.add(FakeCallerInfo(name2, number2))
        }

        val name3 = binding.fakeCallerName3.text.toString()
        val number3 = binding.fakeCallerNumber3.text.toString()
        if (name3.isNotBlank() || number3.isNotBlank()) {
            callers.add(FakeCallerInfo(name3, number3))
        }

        database.child("fake_caller_info").setValue(callers).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Fake caller information saved.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to save fake caller information.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
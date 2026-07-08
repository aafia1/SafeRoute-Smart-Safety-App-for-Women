package com.example.saferoute

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saferoute.databinding.ActivityYourInformationBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class UserInformation(val name: String = "", val address: String = "", val phone: String = "", val email: String = "")

class YourInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYourInformationBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYourInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("Users")

        loadUserInfo()

        binding.saveButton.setOnClickListener {
            saveUserInfo()
        }
    }

    private fun loadUserInfo() {
        database.child("user_info").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(UserInformation::class.java)
                if (userInfo != null) {
                    binding.yourName.setText(userInfo.name)
                    binding.yourAddress.setText(userInfo.address)
                    binding.yourPhone.setText(userInfo.phone)
                    binding.yourEmail.setText(userInfo.email)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@YourInformationActivity, "Failed to load user information.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveUserInfo() {
        val name = binding.yourName.text.toString()
        val address = binding.yourAddress.text.toString()
        val phone = binding.yourPhone.text.toString()
        val email = binding.yourEmail.text.toString()

        val userInfo = UserInformation(name, address, phone, email)

        database.child("user_info").setValue(userInfo).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "User information saved.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to save user information.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
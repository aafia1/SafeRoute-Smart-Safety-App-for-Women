package com.example.saferoute


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.saferoute.databinding.ActivitySplashBinding


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {


    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.splashLogo.alpha = 0f
        binding.splashLogo.animate().alpha(1f).setDuration(1500).withEndAction {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}

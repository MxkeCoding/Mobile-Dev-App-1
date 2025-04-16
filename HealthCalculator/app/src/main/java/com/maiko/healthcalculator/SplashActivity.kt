package com.maiko.healthcalculator

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashImage = ImageView(this)
        splashImage.setImageResource(R.drawable.health_calc)  // uses your splash image
        splashImage.scaleType = ImageView.ScaleType.CENTER_CROP
        setContentView(splashImage)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000) // splash stays for 2 seconds
    }
}
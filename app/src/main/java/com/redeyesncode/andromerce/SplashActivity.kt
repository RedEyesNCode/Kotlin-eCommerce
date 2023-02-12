package com.redeyesncode.andromerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity,LoginActivity::class.java))

        },2000)

        setContentView(R.layout.activity_splash)
    }
}
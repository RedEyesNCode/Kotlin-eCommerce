package com.redeyesncode.andromerce.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.ui.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))

        },2000)

        setContentView(R.layout.activity_splash)
    }
}
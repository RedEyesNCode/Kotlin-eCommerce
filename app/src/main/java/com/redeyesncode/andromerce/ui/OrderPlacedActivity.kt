package com.redeyesncode.andromerce.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.databinding.ActivityOrderPlacedBinding

class OrderPlacedActivity : BaseActivity() {

    lateinit var binding:ActivityOrderPlacedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderPlacedBinding.inflate(layoutInflater)
        initClicks()
        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.btnLogin.setTvButtonText("Thanks for Ordering \n Please wait !")
        binding.btnLogin.showProgress("Thanks ! \nWe will connect with you soon !")
        Handler().postDelayed(Runnable {
            val intentDashboard = Intent(this@OrderPlacedActivity, DashboardActivity::class.java)
            intentDashboard.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentDashboard)
        },5000)

    }
}
package com.redeyesncode.andromerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.redeyesncode.andromerce.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        initClicks()

        setContentView(binding.root)
    }

    private fun isValidated():Boolean{
        return false



    }

    private fun initClicks() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this,DashboardActivity::class.java))
            finish()
        }
        binding.callSignupLayout.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
            finish()
        }


    }
}
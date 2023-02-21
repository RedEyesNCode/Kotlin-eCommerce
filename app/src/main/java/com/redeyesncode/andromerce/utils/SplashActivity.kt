package com.redeyesncode.andromerce.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.FirebaseApp
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.ui.DashboardActivity
import com.redeyesncode.andromerce.ui.LoginActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed(Runnable {
                                       if(AppSession(this@SplashActivity).getBoolean(Constant.IS_LOGGED_IN)){
                                           val intentDashboard =Intent(this@SplashActivity, DashboardActivity::class.java)
                                           intentDashboard.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                           startActivity(intentDashboard)

                                       }else{
                                           val intentLogin =Intent(this@SplashActivity, LoginActivity::class.java)
                                           intentLogin.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                           startActivity(intentLogin)
                                       }


        },2000)

        setContentView(R.layout.activity_splash)
    }
}
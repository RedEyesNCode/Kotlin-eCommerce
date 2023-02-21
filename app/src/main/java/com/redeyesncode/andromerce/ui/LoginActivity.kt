package com.redeyesncode.andromerce.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.databinding.ActivityLoginBinding
import com.redeyesncode.andromerce.presentation.LoginViewModel
import com.redeyesncode.andromerce.utils.AppSession
import com.redeyesncode.andromerce.utils.Constant

class LoginActivity : BaseActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        initClicks()
        setupViewModel()
        attachObservers()

        setContentView(binding.root)
    }

    private fun attachObservers() {
        loginViewModel.isFailed.observe((this)){
            hideLoader()
            if(binding.btnLogin.isLoading){
                binding.btnLogin.setTvButtonText("Login")
                binding.btnLogin.hideProgress("Login")

            }
            if(it!=null){
                showToast(it)
            }
        }
        loginViewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        loginViewModel.loginResponse.observe((this)){
            hideLoader()
            // Store the data into the session and move to dashboard.
            AppSession(this@LoginActivity).putObject(Constant.USER_INFO,it)
            AppSession(this@LoginActivity).put(Constant.IS_LOGGED_IN,true)
            //Move to dashboard
            binding.btnLogin.showProgress("Please wait !")
            Handler().postDelayed(Runnable {
                val intentDashboard =Intent(this@LoginActivity, DashboardActivity::class.java)
                intentDashboard.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intentDashboard)


            },3500)


        }

    }

    private fun setupViewModel() {
        loginViewModel = LoginViewModel()
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewmodel = loginViewModel


    }

    private fun isValidated():Boolean{
        if(binding.edtEmail.text.toString().isEmpty()){
            binding.edtEmail.setError("Please enter email address")
            return false
        }else if(binding.edtPassword.text.toString().isEmpty()){
            binding.edtPassword.setError("Please enter your password")
            return false
        }else{
            return true

        }



    }

    private fun initClicks() {
        binding.btnLogin.setOnClickListener {



        }
        binding.btnLogin.setTvButtonText("Login")
        binding.btnLogin.setOnClickListener {
            val loginHashMap = HashMap<String,String>()
            if(isValidated()){
                binding.btnLogin.showProgress("Loading")

                loginHashMap.put("email",binding.edtEmail.text.toString().trim())
                loginHashMap.put("password",binding.edtPassword.text.toString().trim())
//                showLoader()
                loginViewModel.loginUser(loginHashMap)



            }



        }

        binding.callSignupLayout.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }

        binding.tvForgotPassword.setOnClickListener {

            val intentForgotPassword = Intent(this@LoginActivity,ForgotPasswordActivity::class.java)
            startActivity(intentForgotPassword)


        }




    }
}
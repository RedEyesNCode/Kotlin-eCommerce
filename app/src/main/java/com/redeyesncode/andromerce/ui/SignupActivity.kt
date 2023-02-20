package com.redeyesncode.andromerce.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.SignupUserBody
import com.redeyesncode.andromerce.databinding.ActivitySignupBinding
import com.redeyesncode.andromerce.databinding.BottomSheetOtpBinding
import com.redeyesncode.andromerce.presentation.SignupViewModel
import com.redeyesncode.andromerce.utils.CustomEditTextWithButton
import java.util.concurrent.TimeUnit

class SignupActivity : BaseActivity(),CustomEditTextWithButton.onEvent {

    lateinit var binding:ActivitySignupBinding
    lateinit var signUpViewModel:SignupViewModel
    var storedVerificationId = ""
    var resendToken =""
    lateinit var auth:FirebaseAuth
    lateinit var bottomSheetDialog:BottomSheetDialog
    override fun onVerifyOtpClick(enteredOtp: String?) {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId,
            enteredOtp.toString()
        )
        showLoader()
        signInWithPhoneAuthCredential(credential)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setupViewModel()
        attachObservers()
        initClicks()
        val firebaseApp = FirebaseApp.initializeApp(this@SignupActivity)

        auth = FirebaseAuth.getInstance(firebaseApp!!)
        setContentView(binding.root)
    }

    private fun initClicks() {
        binding.btnNext.setOnClickListener { 
            if(isValidated()){
                showLoader()
//                signUpViewModel.signupUser(getSignupBody())
                // First verify number from firebase then send Data to server.
                verifyNumberFromFirebase("+91"+binding.edtPhoneNumber.text.toString().trim())
            }
        }
        
        
        
    }

    private fun isValidated(): Boolean {
        if(binding.edtName.text.toString().isEmpty()){
            binding.edtName.setError("Please enter your name")
            return false
        }else if(binding.edtEmail.text.toString().isEmpty()){

            binding.edtEmail.setError("Please enter email address")
            return false

        }else if(binding.edtPhoneNumber.text.toString().isEmpty()){
            binding.edtPhoneNumber.setError("Please enter your phone number")
            return false
        }else if(binding.edtPhoneNumber.text.toString().length<10){
            binding.edtPhoneNumber.setError("Please enter valid phone number")
            return false
        }else if(binding.edtPassword.text.toString().isEmpty()){
            binding.edtPassword.setError("Please enter your password")
            return false
        }else if(binding.edtConfirmPassword.text.toString().isEmpty()){
            binding.edtConfirmPassword.setError("Pleae enter password again")
            return false

        }else {
            return true
        }


    }
    private fun verifyNumberFromFirebase(number:String){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)




    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = task.result?.user
                    hideLoader()
                    if(bottomSheetDialog.isShowing){
                        bottomSheetDialog.dismiss()
                    }
                    if(task.isSuccessful){
                        showToast("Otp Verified Successfully !")
                        // Moving to next dashboard with userId in the session.
                        signUpViewModel.signupUser(getSignupBody())



                    }

                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        hideLoader()
                        if(bottomSheetDialog.isShowing){
                            bottomSheetDialog.dismiss()
                        }
                        showToast(task.exception.toString())
                    }
                    // Update UI
                }
            }
    }


    var  callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request

                showDialogImportantAlert(e.toString())

            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                showToast(e.toString())


            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
//            val credential = PhoneAuthProvider.getCredential(verificationId!!, ENTERED_OTP_SHOULD_B_PLACED_HERE)

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            resendToken = token.toString()
            // show the bottom Sheet for OTP verification.
            hideLoader()

            showBottomSheetOtp(storedVerificationId,token)

        }
    }

    private fun showBottomSheetOtp(
        storedVerificationId: String,
        token: PhoneAuthProvider.ForceResendingToken
    ) {
        val bottomSheetBinding = BottomSheetOtpBinding.inflate(LayoutInflater.from(this@SignupActivity))
        bottomSheetDialog = BottomSheetDialog(this,R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
        bottomSheetBinding.btnCustomEditText.setOnVerifyButtonClickListener(this)





    }


    private fun getSignupBody(): SignupUserBody {
        val signupUserBody = SignupUserBody()
        signupUserBody.lastName = binding.edtName.text.toString().trim()
        signupUserBody.firstName = binding.edtName.text.toString().trim()

        signupUserBody.isVerified = true
        signupUserBody.telephone = binding.edtPhoneNumber.text.toString().trim()
        signupUserBody.password = binding.edtPassword.text.toString().trim()
        signupUserBody.email = binding.edtEmail.text.toString().trim()


        return signupUserBody
    }

    private fun attachObservers() {
        signUpViewModel.isFailed.observe((this)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        signUpViewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        signUpViewModel.commonResponse.observe((this)){
            // save the user id in the session and proceed forward.
            hideLoader()
            val signupIntent = Intent(this, DashboardActivity::class.java)
            signupIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(signupIntent)





        }
    }

    private fun setupViewModel() {
        signUpViewModel = SignupViewModel()
        signUpViewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        binding.viewmodel = signUpViewModel



    }
}
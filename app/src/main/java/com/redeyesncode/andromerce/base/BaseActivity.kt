package com.redeyesncode.andromerce.base

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

open class BaseActivity():AppCompatActivity() {
    lateinit var commonDialogBox: CommonDialogBox

    lateinit var commonProgressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commonDialogBox = CommonDialogBox(this)

        commonProgressDialog = ProgressDialog(this)
        commonProgressDialog.setCancelable(false)
        commonProgressDialog.setCanceledOnTouchOutside(false)
        commonProgressDialog.setTitle("Please wait")
        commonProgressDialog.setMessage("Loading....")

        // Disable night theme for the entire app.

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


    }
    fun showDialogImportantAlert(message:String){
        commonDialogBox.showDialog(message,"Important Alert")
    }
    fun showDialog(message: String,title:String){
        commonDialogBox.showDialog(message,title)
    }
    fun showLoader(){
        commonProgressDialog.show()

    }

    fun hideLoader(){
        commonProgressDialog.hide()


    }


    public fun showToast(message:String){
        Toast.makeText(this@BaseActivity,message, Toast.LENGTH_SHORT).show()

    }

    public fun showLog(message:String){
        Log.i("DEV_ASHUTOSH", "showLog: $message")


    }

}
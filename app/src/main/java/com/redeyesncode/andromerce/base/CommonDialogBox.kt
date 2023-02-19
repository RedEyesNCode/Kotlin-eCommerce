package com.redeyesncode.andromerce.base

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.redeyesncode.andromerce.R

class CommonDialogBox(context: Context) {

    lateinit var dialog: Dialog

    init {
        dialog = Dialog(context, R.style.RoundedCornersDialog)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.common_dialog_box)
    }

    fun showDialog(dialogText:String,dialogTitleInput:String){
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = lp
        dialog.show()
        val btnOk: LinearLayout
        val mainLayout: LinearLayout
        mainLayout = dialog.findViewById(R.id.mainLinearLayout)
        mainLayout.fitsSystemWindows = true
        val requiredText: TextView
        val dialogTitle: TextView
        dialogTitle = dialog.findViewById(R.id.tvDialogTitle)
        dialogTitle.text = dialogTitleInput
        requiredText = dialog.findViewById(R.id.tvSetText)
        requiredText.setText(dialogText)
        btnOk = dialog.findViewById(R.id.btnOk)
        btnOk.setOnClickListener { v: View? -> dialog.dismiss() }
    }
}
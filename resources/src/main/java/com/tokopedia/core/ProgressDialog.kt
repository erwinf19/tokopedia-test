package com.tokopedia.core

import android.app.Activity
import android.app.Dialog
import com.tokopedia.resources.R

class ProgressDialog(private val mActivity: Activity) {
    private lateinit var isDialog : Dialog
    fun startLoading(){
        isDialog = Dialog(mActivity)
        isDialog.setContentView(R.layout.progress_dialog)
        isDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        isDialog.show()
    }

    fun isDismiss(){
        isDialog.dismiss()
    }
}
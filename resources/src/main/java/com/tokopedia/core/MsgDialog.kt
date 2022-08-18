package com.tokopedia.core

import android.app.Activity
import android.app.Dialog
import android.widget.TextView
import com.tokopedia.resources.R

class MsgDialog(private val mActivity: Activity) {
    private lateinit var isDialog: Dialog
    fun isShow(message: String) {

        isDialog = Dialog(mActivity)
        isDialog.setCancelable(true)
        isDialog.setContentView(R.layout.layout_custom_dialog)
        isDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val tvMsg = isDialog.findViewById(R.id.tvMsg) as TextView
        tvMsg.text = message
        isDialog.show()
    }

    fun dismissDialog() {
        isDialog.dismiss()
    }
}
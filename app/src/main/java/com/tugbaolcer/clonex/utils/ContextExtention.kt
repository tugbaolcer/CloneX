package com.tugbaolcer.clonex.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.tugbaolcer.clonex.CloneXApp.Companion.mAlertDialog
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.databinding.LayoutAlertBinding

fun Context.showBaseAlert(
    alertType: Int,
    message: String
) {

    val binding = LayoutAlertBinding.inflate(LayoutInflater.from(this))
    AlertDialog.Builder(this).setView(binding.root).show().let { alertDialog ->

        if (mAlertDialog != null && mAlertDialog!!.isShowing)
            try {
                mAlertDialog!!.dismiss()
            } catch (e: Exception) {
                Log.e("DIALOG_ERROR", e.localizedMessage?.toString() ?: "DIALOG_ERROR")
            }
        mAlertDialog = alertDialog

        alertDialog.window?.apply {
            attributes.verticalMargin = 0.02F
            setGravity(Gravity.TOP)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDimAmount(0.1f)
        }

        alertDialog.apply {
            binding.apply {
                when (alertType) {
                    ALERT_ERROR -> {
                        containerCardview.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.lightly_red))
                        mainContainer.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
                        ivAlertIcon.setImageResource(R.drawable.ic_alert_error)
                    }

                    ALERT_INFORMATION -> {
                        containerCardview.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.lightly_blue))
                        mainContainer.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blue))
                        ivAlertIcon.setImageResource(R.drawable.ic_alert_information)
                    }

                    ALERT_SUCCESSFUL -> {
                        containerCardview.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.lightly_green))
                        mainContainer.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green))
                        ivAlertIcon.setImageResource(R.drawable.ic_alert_success)
                    }

                    ALERT_WARNING -> {
                        containerCardview.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.lightly_yellow))
                        mainContainer.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.yellow))
                        ivAlertIcon.setImageResource(R.drawable.ic_alert_warning)
                    }
                }
                tvMessage.text = message
                ibCancel.setOnSingleClickListener { dismiss() }
            }
            show()
        }
    }
}

fun Context.showErrorAlert(
    message: String
) {
    showBaseAlert(ALERT_ERROR, message)
}

fun Context.showSuccessAlert(
    message: String
) {
    showBaseAlert(ALERT_SUCCESSFUL, message)
}

fun Context.showInfoAlert(
    message: String
) {
    showBaseAlert(ALERT_INFORMATION, message)
}

fun Context.showWarningAlert(
    message: String
) {
    showBaseAlert(ALERT_WARNING, message)
}
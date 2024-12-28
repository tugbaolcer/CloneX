package com.tugbaolcer.clonex.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import com.tugbaolcer.clonex.databinding.DialogProgressBinding


class ProgressDialog(context: Context) : Dialog(context), View.OnClickListener {

    private var binding: DialogProgressBinding = DialogProgressBinding.inflate(LayoutInflater.from(context))

    override fun onClick(p0: View?) {}

    init {
        setContentView(binding.root)

        binding.progress.apply {
            background = ColorDrawable(Color.TRANSPARENT)
            isIndeterminate = true
            indeterminateDrawable.setColorFilter(
                -0x1,
                android.graphics.PorterDuff.Mode.MULTIPLY
            )
        }
        if (window != null) {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        binding.dialogWrapper.setOnClickListener(this)
        binding.progress.setOnClickListener(this)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    fun showText(isTextVisible: Boolean) {
        binding.tvText.visibility = if (isTextVisible) View.VISIBLE else View.GONE
    }

}

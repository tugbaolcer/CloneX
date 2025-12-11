package com.tugbaolcer.clonex.utils

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.databinding.LayoutCustomEditTextBinding
import kotlin.toString

class CustomEditTextView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    var binding = LayoutCustomEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    private var isPasswordVisible = false
    private var textChangeListener: ((String) -> Unit)? = null

    fun setText(text: String) {
        this.binding.etCustom.setText(text)
    }

    val inputText: String
        get() = binding.etCustom.text.toString()


    init {
        binding.apply {
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomEditTextView, 0, 0)
                .let { typedArray ->
                    try {

                        val hintString =
                            typedArray.getString(R.styleable.CustomEditTextView_til_hint)
                        tilCustom.hint = hintString

                        val isShowButton =
                            typedArray.getBoolean(
                                R.styleable.CustomEditTextView_til_show_password,
                                false
                            )

                        showPasswordButton.visibility =
                            if (isShowButton) VISIBLE else GONE

                        etCustom.addTextChangedListener {
                            textChangeListener?.invoke(it.toString())
                        }


                    } finally {
                        root.invalidate()
                        typedArray.recycle()
                    }
                }

            setupButtonClickListener()
        }
    }

    private fun setupButtonClickListener() {
        binding.showPasswordButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            updatePasswordVisibility()
        }
    }

    private fun updatePasswordVisibility() {
        binding.apply {
            if (isPasswordVisible) {
                etCustom.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPasswordButton.text = context.getString(R.string.Common_Label_Gone)
            } else {
                etCustom.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordButton.text = context.getString(R.string.Common_Label_Show)
            }
            etCustom.setSelection(etCustom.text?.length ?: 0)
        }
    }

    fun setOnTextChangeListener(listener: (String) -> Unit) {
        textChangeListener = listener
    }
}
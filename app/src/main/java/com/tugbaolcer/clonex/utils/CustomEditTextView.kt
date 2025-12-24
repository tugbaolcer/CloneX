package com.tugbaolcer.clonex.utils

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.databinding.LayoutCustomEditTextBinding
import kotlin.toString

class CustomEditTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding =
        LayoutCustomEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    private var isPasswordField = false
    private var isPasswordVisible = false
    private var textChangeListener: ((String) -> Unit)? = null

    val inputText: String
        get() = binding.etCustom.text.toString()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomEditTextView,
            0,
            0
        ).apply {
            try {
                binding.tilCustom.hint =
                    getString(R.styleable.CustomEditTextView_til_hint)

                isPasswordField = getBoolean(
                    R.styleable.CustomEditTextView_til_show_password,
                    false
                )

                binding.showPasswordButton.visibility =
                    if (isPasswordField) VISIBLE else GONE
            } finally {
                recycle()
            }
        }

        setupTextWatcher()
        setupButtonClickListener()
        applyInitialState()
    }

    private fun applyInitialState() {
        if (isPasswordField) {
            // Başlangıçta gizli (noktalı)
            binding.etCustom.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            binding.showPasswordButton.text =
                context.getString(R.string.Common_Label_Show)
        } else {
            // Normal text
            binding.etCustom.inputType = InputType.TYPE_CLASS_TEXT
        }
    }

    private fun setupButtonClickListener() {
        binding.showPasswordButton.setOnClickListener {
            if (!isPasswordField) return@setOnClickListener

            isPasswordVisible = !isPasswordVisible
            updatePasswordVisibility()
        }
    }

    private fun updatePasswordVisibility() {
        binding.apply {
            if (isPasswordVisible) {
                etCustom.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPasswordButton.text =
                    context.getString(R.string.Common_Label_Gone)
            } else {
                etCustom.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordButton.text =
                    context.getString(R.string.Common_Label_Show)
            }
            etCustom.setSelection(etCustom.text?.length ?: 0)
        }
    }

    private fun setupTextWatcher() {
        binding.etCustom.addTextChangedListener {
            textChangeListener?.invoke(it.toString())
        }
    }

    fun setOnTextChangeListener(listener: (String) -> Unit) {
        textChangeListener = listener
    }

    fun setText(text: String) {
        binding.etCustom.setText(text)
    }
}
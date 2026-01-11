package com.tugbaolcer.clonex.utils

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.tugbaolcer.clonex.R
import com.tugbaolcer.clonex.ui.main.ChipUIState
import com.tugbaolcer.clonex.ui.onboarding.TabOnBoardingViewModel

/**
 * created view by Tugba OLCER
 * date: 1.1.25
 */

@BindingAdapter("image_type")
fun setOnBoardingImage(view: AppCompatImageView, step_type: TabOnBoardingViewModel.OnBoardingType) {
    val image = when (step_type) {
        TabOnBoardingViewModel.OnBoardingType.STEP_ONE -> R.drawable.onboarding_image_1
        TabOnBoardingViewModel.OnBoardingType.STEP_TWO -> R.drawable.onboarding_image_2
        TabOnBoardingViewModel.OnBoardingType.STEP_THREE -> R.drawable.onboarding_image_3
        TabOnBoardingViewModel.OnBoardingType.STEP_FOUR -> R.drawable.onboarding_image_1
    }

    view.setImageResource(image)

    if (step_type == TabOnBoardingViewModel.OnBoardingType.STEP_FOUR)
        view.visibility = View.INVISIBLE
}

@BindingAdapter("title_type")
fun setOnBoardingTextTitle(
    view: AppCompatTextView,
    step_type: TabOnBoardingViewModel.OnBoardingType
) {
    val text = when (step_type) {
        TabOnBoardingViewModel.OnBoardingType.STEP_ONE -> view.context.getString(R.string.Onboarding_Label_Title1)
        TabOnBoardingViewModel.OnBoardingType.STEP_TWO -> view.context.getString(R.string.Onboarding_Label_Title2)
        TabOnBoardingViewModel.OnBoardingType.STEP_THREE -> view.context.getString(R.string.Onboarding_Label_Title3)
        TabOnBoardingViewModel.OnBoardingType.STEP_FOUR -> view.context.getString(R.string.Onboarding_Label_Title4)
    }
    view.text = text
}

@BindingAdapter("desc_type")
fun setOnBoardingTextDesc(
    view: AppCompatTextView,
    step_type: TabOnBoardingViewModel.OnBoardingType
) {
    val text = when (step_type) {
        TabOnBoardingViewModel.OnBoardingType.STEP_ONE -> view.context.getString(R.string.Onboarding_Label_Desc1)
        TabOnBoardingViewModel.OnBoardingType.STEP_TWO -> view.context.getString(R.string.Onboarding_Label_Desc2)
        TabOnBoardingViewModel.OnBoardingType.STEP_THREE -> view.context.getString(R.string.Onboarding_Label_Desc3)
        TabOnBoardingViewModel.OnBoardingType.STEP_FOUR -> view.context.getString(R.string.Onboarding_Label_Desc4)
    }
    view.text = text
}

@BindingAdapter("setClickableText")
fun setClickableText(textView: TextView, content: String) {
    val spannableString = SpannableString(content)
    val clickableText = "netflix.com/more"

    val startIndex = content.indexOf(clickableText)
    val endIndex = startIndex + clickableText.length
    if (startIndex != -1) {
        spannableString.setSpan(
            ForegroundColorSpan(Color.BLUE),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val playStoreIntent = Intent(Intent.ACTION_VIEW).apply {
                        data =
                            Uri.parse("https://play.google.com/store/apps/details?id=com.netflix.mediaclient")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    widget.context.startActivity(playStoreIntent)
                }
            },
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    textView.text = spannableString
    textView.movementMethod = android.text.method.LinkMovementMethod.getInstance()
}

@BindingAdapter("is_visible")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("isEnabled")
fun setButtonEnabled(button: View, isEnabled: Boolean?) {
    button.isEnabled = isEnabled == true
}

@BindingAdapter("text")
fun setCustomEditText(view: CustomEditTextView, value: String?) {
    if (view.inputText != value) {
        view.setText(value ?: "")
    }
}

@InverseBindingAdapter(attribute = "text")
fun getCustomEditText(view: CustomEditTextView): String {
    return view.inputText
}

@BindingAdapter("textAttrChanged")
fun setCustomEditTextListener(view: CustomEditTextView, listener: InverseBindingListener) {
    view.setOnTextChangeListener {
        listener.onChange()
    }
}

@BindingAdapter("closeButtonVisibility")
fun setCloseButtonVisibility(view: View, state: ChipUIState) {
    view.visibility = if (state is ChipUIState.Home) View.GONE else View.VISIBLE
}

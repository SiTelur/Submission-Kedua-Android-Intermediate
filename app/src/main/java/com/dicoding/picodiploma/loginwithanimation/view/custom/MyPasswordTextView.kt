package com.dicoding.picodiploma.loginwithanimation.view.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.picodiploma.loginwithanimation.R
import com.google.android.material.textfield.TextInputLayout

class MyPasswordTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs) {
    private var errorMessage: String = resources.getString(R.string.error_password)

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    setError(errorMessage)

                } else {
                    setError(null)
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }


    private fun setError(error: String?) {
        (parent.parent as? TextInputLayout)?.let { textInputLayout ->
            textInputLayout.error = error
            textInputLayout.isErrorEnabled = error != null
            textInputLayout.errorIconDrawable = null  // This removes the error icon
        }
    }

}
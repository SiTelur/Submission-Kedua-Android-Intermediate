package com.dicoding.picodiploma.loginwithanimation.view.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.picodiploma.loginwithanimation.R
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


class MyEmailTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs) {

    private var errorMessage: String = resources.getString(R.string.error_email)

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!isValidEmail(s.toString())) {
                    setError(errorMessage)

                } else {
                    setError(null)
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun setError(error: String?) {
        (parent.parent as? TextInputLayout)?.let { textInputLayout ->
            textInputLayout.error = error
            textInputLayout.errorIconDrawable = null  // This removes the error icon
        }
    }
}
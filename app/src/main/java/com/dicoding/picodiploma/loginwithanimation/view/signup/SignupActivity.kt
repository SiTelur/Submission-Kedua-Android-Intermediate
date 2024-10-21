package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.Result
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private val signupViewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)

        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEditText =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)

        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditText =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)

        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditText =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)

        val button = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)
        val name = AnimatorSet().apply {
            playTogether(nameTextView, nameEditText)
        }

        val email = AnimatorSet().apply {
            playTogether(emailTextView, emailEditText)
        }

        val password = AnimatorSet().apply {
            playTogether(passwordTextView, passwordEditText)
        }

        AnimatorSet().apply {
            playSequentially(title, name, email, password, button)
            start()
        }

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }


    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (isFormValid()) {
                signupViewModel.registerNew(name, email, password).observe(this) { result ->
                    when (result) {
                        is Result.Error -> showErrorDialog(result.error)
                        Result.Loading -> showLoading(true)
                        is Result.Success -> showSuccessDialog(email)
                    }
                }
            } else {
                Toast.makeText(this, "Isi Form Terlebih Dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isFormValid(): Boolean {
        return !(binding.emailEditTextLayout.error != null || binding.passwordEditTextLayout.error != null)
    }


    private fun showSuccessDialog(email: String) {
        showLoading(false)
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.yeah))
            setMessage(getString(R.string.account_created_message, email))
            setPositiveButton(getString(R.string.next)) { _, _ ->
                finish()
            }
            create()
            show()
        }
    }

    private fun showErrorDialog(errorMessage: String) {
        showLoading(false)
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.account_failed_message))
            setMessage(errorMessage)
            setPositiveButton("Ok") { _, _ ->
            }
            create()
            show()
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.signupButton.isEnabled = !isLoading
    }
}
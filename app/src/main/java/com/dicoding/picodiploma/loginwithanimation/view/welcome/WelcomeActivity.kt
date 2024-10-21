package com.dicoding.picodiploma.loginwithanimation.view.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityWelcomeBinding
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginActivity
import com.dicoding.picodiploma.loginwithanimation.view.signup.SignupActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        setupAnimation()
    }

    private fun setupAnimation() {
        val picture = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(1000)

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(1000)

        val subtitle =
            ObjectAnimator.ofFloat(binding.descTextView, View.ALPHA, 1f).setDuration(1000)

        val btnMasuk = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(1000)
        val btnDaftar =
            ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(1000)

        val together = AnimatorSet().apply {
            playTogether(btnDaftar, btnMasuk)
        }

        AnimatorSet().apply {
            playSequentially(picture, title, subtitle, together)
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
        binding.loginButton.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java),
                ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()
            )
        }

        binding.signupButton.setOnClickListener {
            startActivity(
                Intent(this, SignupActivity::class.java),
                ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()
            )
        }
    }
}
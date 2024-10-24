package com.dicoding.picodiploma.loginwithanimation.view.detail

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.local.StoryPerson
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val DETAIL_ITEM = "DetailItem"
    }

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
        val bundle = intent.extras

        bundle?.let { item ->
            @Suppress("DEPRECATION") val data =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    item.getParcelable(DETAIL_ITEM, StoryPerson::class.java)
                } else {
                    item.getParcelable(DETAIL_ITEM)
                }
            if (data != null) {
                showContent(data)
            }
        }
    }

    private fun showContent(item: StoryPerson) {
        with(binding) {
            Glide.with(this@DetailActivity).load(item.photoUrl).into(ivDetailPhoto)
            tvDetailName.text = item.name
            tvDetailDescription.text = item.description
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
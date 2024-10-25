package com.dicoding.picodiploma.loginwithanimation.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.local.StoryPerson
import com.dicoding.picodiploma.loginwithanimation.databinding.StoryItemLayoutBinding
import com.dicoding.picodiploma.loginwithanimation.view.detail.DetailActivity

class StoryAdapter : PagingDataAdapter<StoryPerson, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    inner class StoryViewHolder(private val binding: StoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoryPerson) {
            Glide.with(binding.root.context)
                .load(item.photoUrl)
                .into(binding.ivItemPhoto)

            binding.tvItemName.text = item.name

            binding.root.setOnClickListener {

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.tvItemName, "uploader"),
                        Pair(binding.ivItemPhoto, "picture"),
                    )
                val data = Bundle().apply {
                    putParcelable(DetailActivity.DETAIL_ITEM, item)
                }

                val intent =
                    Intent(binding.root.context, DetailActivity::class.java).putExtras(data)
                binding.root.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryPerson>() {
            override fun areItemsTheSame(
                oldItem: StoryPerson,
                newItem: StoryPerson,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StoryPerson,
                newItem: StoryPerson,
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding =
            StoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
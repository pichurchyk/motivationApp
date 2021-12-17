package com.pichurchyk.motivationapp.ui.achievementsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pichurchyk.motivationapp.data.db.AchievementEntity
import com.pichurchyk.motivationapp.databinding.AchievementItemBinding

class AchievementsAdapter(val deleteItem: ItemClick) :
    ListAdapter<AchievementEntity, AchievementsAdapter.mViewHolder>(COMPARATOR) {

    var isEditable = false

    inner class mViewHolder(private val binding: AchievementItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AchievementEntity) {
            binding.dateDay.text = item.date.substring(0, 6)
            binding.dateYear.text = item.date.substring(7, 11)
            binding.text.text = item.text

            binding.deleteBtn.setOnClickListener {
                deleteItem.deleteAchievement(item)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<AchievementEntity>() {
            override fun areItemsTheSame(oldItem: AchievementEntity, newItem: AchievementEntity) =
                oldItem.date == newItem.date

            override fun areContentsTheSame(
                oldItem: AchievementEntity,
                newItem: AchievementEntity
            ) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        return mViewHolder(
            AchievementItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    interface ItemClick {
        fun deleteAchievement(achievement: AchievementEntity)
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
}
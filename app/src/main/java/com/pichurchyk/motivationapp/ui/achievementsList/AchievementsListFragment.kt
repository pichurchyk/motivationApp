package com.pichurchyk.motivationapp.ui.achievementsList

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pichurchyk.motivationapp.R
import com.pichurchyk.motivationapp.data.db.AchievementEntity
import com.pichurchyk.motivationapp.databinding.FragmentAchievementsListBinding
import com.pichurchyk.motivationapp.ui.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.DividerItemDecoration




@AndroidEntryPoint
class AchievementsListFragment :
    BaseFragment<AchievementsListViewModel, FragmentAchievementsListBinding>(
        R.layout.fragment_achievements_list,
        AchievementsListViewModel::class.java
    ), AchievementsAdapter.ItemClick {

    private var isEditing = false

    lateinit var adapter: AchievementsAdapter

    override fun init() {
        super.init()

        binding.title.animate().translationX(0f).duration = 500

        viewModel.currentAchievementText.value?.let {
            binding.input.setText(it)
        }

        setUpAdapter()
        setListeners()
    }

    private fun setListeners() {
        binding.input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.setNewParams(p0.toString())
            }
        })

        binding.root.setOnClickListener {
            clearFocus()
        }

        binding.submit.setOnClickListener {
            viewModel.insertAchievement()
            clearFocus()
            binding.input.setText("")
        }

        lifecycleScope.launch {
            viewModel.achievementList.collect {
                adapter.submitList(it)
                if (it.isEmpty()) {
                    binding.emptyAnnotation.visibility = View.VISIBLE
                } else {
                    binding.emptyAnnotation.visibility = View.GONE
                }
            }
        }
    }

    private fun setUpAdapter() {
        adapter = AchievementsAdapter(this)
        val recyclerView = binding.list
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        val dividerDecoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerDecoration)
        recyclerView.adapter = adapter
    }

    override fun deleteAchievement(achievement: AchievementEntity) {
        viewModel.deleteAchievement(achievement)
    }
}
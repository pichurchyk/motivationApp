package com.pichurchyk.motivationapp.ui.achievementsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pichurchyk.motivationapp.data.db.AchievementEntity
import com.pichurchyk.motivationapp.data.repo.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AchievementsListViewModel @Inject constructor(private val localRepository: LocalRepository
) : ViewModel() {

    private val _currentAchievementText = MutableStateFlow("")
    val currentAchievementText = _currentAchievementText.asStateFlow()

    val achievementList = localRepository.allAchievements

    fun insertAchievement() {
        viewModelScope.launch(Dispatchers.IO) {
            val achievement = AchievementEntity( getCurrentDate(), currentAchievementText.value)
            localRepository.insertAchievement(achievement)
        }
    }
    fun deleteAchievement(achievement: AchievementEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.deleteAchievement(achievement)
        }
    }

    fun setNewParams(newText: String) {
        _currentAchievementText.value = newText
    }

    private fun getCurrentDate() : String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
}
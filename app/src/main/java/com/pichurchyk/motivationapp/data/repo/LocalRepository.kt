package com.pichurchyk.motivationapp.data.repo

import com.pichurchyk.motivationapp.data.db.AchievementEntity
import com.pichurchyk.motivationapp.data.db.AchievementsDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(private val dao: AchievementsDao) {

    fun insertAchievement(achievement: AchievementEntity) = dao.insertAchievement(achievement)
    fun deleteAchievement(achievement: AchievementEntity) = dao.deleteAchievement(achievement)

    val allAchievements = dao.getAchievements()
}
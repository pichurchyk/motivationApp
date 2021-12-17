package com.pichurchyk.motivationapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementsDao {

    @Insert(onConflict = REPLACE)
    fun insertAchievement(achievement: AchievementEntity)

    @Query("Select * from Achievements ORDER BY abs(date)")
    fun getAchievements() : Flow<List<AchievementEntity>>

    @Delete
    fun deleteAchievement(achievement: AchievementEntity)
}
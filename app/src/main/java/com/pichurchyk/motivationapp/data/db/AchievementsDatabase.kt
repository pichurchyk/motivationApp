package com.pichurchyk.motivationapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AchievementEntity::class], version = 1)
abstract class AchievementsDatabase : RoomDatabase() {
    abstract fun dao() : AchievementsDao
}
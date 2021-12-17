package com.pichurchyk.motivationapp.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Achievements")
data class AchievementEntity(

    @PrimaryKey(autoGenerate = false)
    val date: String,
    val text: String
) : Parcelable
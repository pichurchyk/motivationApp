package com.pichurchyk.motivationapp.ui.motivationPage

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pichurchyk.motivationapp.data.repo.RemoteRepository
import com.pichurchyk.motivationapp.ui.common.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MotivationPageViewModel @Inject constructor(
    private val repository: RemoteRepository,
    private val pref: SharedPreferences
) :
    ViewModel() {

    var isLoading = 0

    private var _dailyQuote = repository.getQuoteOfTheDay().map {
        it
    }.asLiveData(Dispatchers.Default)

    val dailyQuote = _dailyQuote

    private var _randomQuote = repository.getRandomQuote().map {
        it
    }.asLiveData(Dispatchers.Default)

    val randomQuote = _randomQuote

    fun setNotificationStatusToPref(status: Boolean): ResponseStatus<String> {
        return try {
            pref.edit().putBoolean(SharedPrefConsts.NOTIFICATION_STATUS.sharedPrefName, status)
                .apply()
            if (status) ResponseStatus.Success("Notifications enabled") else ResponseStatus.Success(
                "Notifications disabled"
            )
        } catch (e: Exception) {
            ResponseStatus.Failure(e)
        }
    }

    fun setNotificationTime(hour: String, min: String): ResponseStatus<String> {
        val time = Calendar.getInstance()
        time.set(Calendar.HOUR_OF_DAY, hour.toInt())
        time.set(Calendar.MINUTE, min.toInt())
        time.set(Calendar.SECOND, 0)

        return try {
            pref.edit()
                .putLong(SharedPrefConsts.NOTIFICATION_TIME.sharedPrefName, time.timeInMillis)
                .apply()
            ResponseStatus.Success("Success")
        } catch (e: Exception) {
            ResponseStatus.Failure(e)
        }
    }

    fun getNotificationTime(): Long {
        return pref.getLong(SharedPrefConsts.NOTIFICATION_TIME.sharedPrefName, 0)
    }

    fun getNotificationsStatus(): Boolean {
        return pref.getBoolean(SharedPrefConsts.NOTIFICATION_STATUS.sharedPrefName, false)
    }
}
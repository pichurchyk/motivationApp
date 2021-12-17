package com.pichurchyk.motivationapp.ui.motivationPage

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.view.View
import com.pichurchyk.motivationapp.R
import com.pichurchyk.motivationapp.data.notificationsUtils.AlarmNotificationReceiver
import com.pichurchyk.motivationapp.data.notificationsUtils.NotificationService
import com.pichurchyk.motivationapp.databinding.FragmentMotivationPageBinding
import com.pichurchyk.motivationapp.ui.common.ResponseStatus
import com.pichurchyk.motivationapp.ui.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MotivationPageFragment : BaseFragment<MotivationPageViewModel, FragmentMotivationPageBinding>(
    R.layout.fragment_motivation_page,
    MotivationPageViewModel::class.java
) {

    override fun init() {
        super.init()

        binding.title.animate().translationX(0f).duration = 500
        getRandomQuote()
        binding.notificationSwitch.isChecked = viewModel.getNotificationsStatus()


        viewModel.getNotificationTime().let {
            if (it != 0L) {
                val date = Date(it)
                val calendar = Calendar.getInstance().apply { time = date }
                val hours = calendar.get(Calendar.HOUR_OF_DAY)
                val min = calendar.get(Calendar.MINUTE)

                binding.timePicker.hours.value = hours
                binding.timePicker.minutes.value = min
            }
        }

        setListeners()
    }

    private fun setListeners() {
        binding.notificationSwitch.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.setNotificationStatusToPref(b)
            setupAlarm(b)
            viewModel.setNotificationStatusToPref(b)
        }

        binding.timePicker.hours.setOnValueChangedListener { _, _, newVal ->
            val action = viewModel.setNotificationTime(
                newVal.toString(),
                binding.timePicker.minutes.value.toString()
            )

            if (action is ResponseStatus.Failure) {
                snackBar(
                    requireView(),
                    action.throwable.localizedMessage!!
                )
            }
            changeReceiveTime()
        }

        binding.timePicker.minutes.setOnValueChangedListener { _, _, newVal ->
            val action = viewModel.setNotificationTime(
                binding.timePicker.hours.value.toString(),
                newVal.toString()
            )

            if (action is ResponseStatus.Failure) {
                snackBar(
                    requireView(),
                    action.throwable.localizedMessage!!
                )
            }
            changeReceiveTime()
        }
    }

    private fun changeReceiveTime() {
        val sConn = object : ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {}
            override fun onServiceDisconnected(p0: ComponentName?) {}
        }

        val serviceIntent = Intent(requireContext() ,NotificationService::class.java)
        serviceIntent.putExtra("time", viewModel.getNotificationTime())
        requireContext().bindService(serviceIntent, sConn, Service.BIND_AUTO_CREATE)
    }

    private fun setupAlarm(isEnable : Boolean) {
        val manager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val time = viewModel.getNotificationTime()

        val mIntent = Intent(requireContext(), AlarmNotificationReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(requireContext(), 0, mIntent, 0)

        if (isEnable) {
            requireContext().startService(Intent(requireContext(), NotificationService::class.java))
            manager.setRepeating(AlarmManager.RTC_WAKEUP,time, AlarmManager.INTERVAL_DAY, alarmPendingIntent)
        } else {
            requireContext().stopService(Intent(requireContext(), NotificationService::class.java))
            manager.cancel(alarmPendingIntent)
        }
    }

    private fun getRandomQuote() {
        viewModel.dailyQuote.observe(viewLifecycleOwner, {
            when (it) {
                is ResponseStatus.Success -> binding.quote.text = it.value[0].quote
                is ResponseStatus.Failure -> {
                    binding.quote.text = "Some error occurred"
                }
            }
            binding.loader.visibility = View.GONE
        })
    }
}
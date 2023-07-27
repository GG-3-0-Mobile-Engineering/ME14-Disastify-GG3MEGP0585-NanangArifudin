package com.arfdn.disastify.presentation.customview

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateTimePickerFragment(private val isStartTime: Boolean) : DialogFragment() {

    private val dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ssZZZZ"
    private lateinit var dateTimeListener: DateTimeListener

    interface DateTimeListener {
        fun onDateTimeSet(dateTime: String, isStartTime: Boolean)
    }

    fun setListener(listener: DateTimeListener) {
        dateTimeListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            TimePickerDialog(
                requireContext(), { _, selectedHour, selectedMinute ->
                    val selectedCalendar = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)
                    }
                    val sdf = SimpleDateFormat(dateTimeFormat, Locale.getDefault())
                    val formattedDateTime = sdf.format(selectedCalendar.time)
                    dateTimeListener.onDateTimeSet(formattedDateTime, isStartTime)
                },
                hour, minute, true
            ).show()
        }, year, month, day)
    }
}
package com.arfdn.disastify.presentation.customview

import android.app.Dialog
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.arfdn.disastify.databinding.LayoutFilterTimeBinding
import com.arfdn.disastify.databinding.LayoutWaitingDialogBinding

class TimePickerDialog(
    context: Context,
    fm: FragmentManager,
    val listener: OnClickTimePicker
) : Dialog(context) {
    private val binding by lazy { LayoutFilterTimeBinding.inflate(layoutInflater) }

    init {
        setContentView(binding.root)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                android.R.color.transparent
            )
        )
        binding.apply {

            edtStartTime.setOnClickListener {
                val dateTimePickerFragment = DateTimePickerFragment(true)
                dateTimePickerFragment.setListener(object : DateTimePickerFragment.DateTimeListener {
                    override fun onDateTimeSet(dateTime: String, isStartTime: Boolean) {
                        if (isStartTime) {
                        edtStartTime.setText(dateTime)
                        }
                    }
                })
                dateTimePickerFragment.show(fm, "DateTimePickerFragment")
            }

            edtEndTime.setOnClickListener {
                val dateTimePickerFragment = DateTimePickerFragment(false)
                dateTimePickerFragment.setListener(object : DateTimePickerFragment.DateTimeListener {
                    override fun onDateTimeSet(dateTime: String, isStartTime: Boolean) {
                        if (!isStartTime) {
                            edtEndTime.setText(dateTime)
                        }
                    }
                })
                dateTimePickerFragment.show(fm, "DateTimePickerFragment")
            }
            btnLoadData.setOnClickListener {
                listener.onClickTimePicker(edtStartTime.text.toString(), edtEndTime.text.toString())
            }

        }
    }
}

interface OnClickTimePicker {
    fun onClickTimePicker(startTime:String, endTime:String)
}

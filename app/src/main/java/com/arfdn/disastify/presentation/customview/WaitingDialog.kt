package com.arfdn.disastify.presentation.customview

import android.app.Dialog
import android.content.Context
import androidx.core.content.ContextCompat
import com.arfdn.disastify.databinding.LayoutWaitingDialogBinding

class WaitingDialog(
    context: Context,
    ): Dialog(context) {
    val binding by lazy { LayoutWaitingDialogBinding.inflate(layoutInflater) }
        init {
        setContentView(binding.root)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.transparent))
        binding.apply {

        }

    }
}

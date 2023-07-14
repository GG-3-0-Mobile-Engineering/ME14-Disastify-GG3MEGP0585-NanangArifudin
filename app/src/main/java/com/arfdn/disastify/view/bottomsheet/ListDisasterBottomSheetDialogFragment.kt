package com.arfdn.disastify.view.bottomsheet

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.arfdn.disastify.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ListDisasterBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it) // use let to execute the code only if the view is not null
                behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED // show 1/3 of the screen by default
                behavior.peekHeight = 0 // remove the default peek height
                behavior.isHideable = false // prevent the user from swiping down to hide the bottom sheet
            }

        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE) // remove the title bar
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // make the background transparent
        return inflater.inflate(R.layout.list_disaster_bottom_sheet, container, false) // inflate your custom layout
    }

}
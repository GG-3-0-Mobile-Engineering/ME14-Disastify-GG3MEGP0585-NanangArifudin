package com.arfdn.disastify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.arfdn.disastify.data.Disaster
import com.arfdn.disastify.databinding.ActivityMainBinding
import com.arfdn.disastify.view.bottomsheet.ListDisasterBottomSheetDialogFragment
import com.arfdn.disastify.view.list_disaster.DisasterAdapter
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {

    private val binding by bindActivity(ActivityMainBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding){
            root.setOnClickListener {
                val bottomSheetFragment = ListDisasterBottomSheetDialogFragment()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }

            val chipData = listOf("Banjir", "Kabut", "Badai", "Tsunami", "Gempa")

            for (chipText in chipData) {
                val chip = Chip(this@MainActivity)
                chip.text = chipText
//                chip.setChipIconResource(R.drawable.ic_plus)
                chip.isCheckable = true
                chip.isCloseIconVisible = false
                layoutSearchFilter.chipGroup.addView(chip)
            }
        }
        val bottomSheetFragment = ListDisasterBottomSheetDialogFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

    }


}


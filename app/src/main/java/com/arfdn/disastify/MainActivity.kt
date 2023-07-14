package com.arfdn.disastify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arfdn.disastify.databinding.ActivityMainBinding
import com.arfdn.disastify.view.bottomsheet.ListDisasterBottomSheetDialogFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        val bottomSheetFragment = ListDisasterBottomSheetDialogFragment()
//        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

    }
}
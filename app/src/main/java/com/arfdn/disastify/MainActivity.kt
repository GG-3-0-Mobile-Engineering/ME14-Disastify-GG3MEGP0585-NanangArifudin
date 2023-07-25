package com.arfdn.disastify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.arfdn.disastify.databinding.ActivityMainBinding
import com.arfdn.disastify.presentation.report.DisasterListViewModel
import com.arfdn.disastify.presentation.report.ListDisasterBottomSheetDialogFragment
import com.arfdn.disastify.utils.bindActivity
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by bindActivity(ActivityMainBinding::inflate)
    private val disasterListViewModel: DisasterListViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding){
            root.setOnClickListener {
                val bottomSheetFragment = ListDisasterBottomSheetDialogFragment()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }




//            disasterListViewModel.getDisasterReports()
            disasterListViewModel.disasterList.observe(this@MainActivity, Observer { disasters ->
                // Data retrieval is successful, log the data here
                Log.d("DisasterData", "Disaster Data Retrieved: $disasters")
                // Do further processing or display the data on the UI as needed
            })
        }

    }


}


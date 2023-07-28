package com.arfdn.disastify.presentation.report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfdn.disastify.domain.model.Disaster
import com.arfdn.disastify.domain.usecase.DisasterUseCase
import kotlinx.coroutines.launch

class DisasterListViewModel(private val useCase: DisasterUseCase) : ViewModel() {
    private val _disasterList = MutableLiveData<Disaster>()
    val disasterList: LiveData<Disaster> get() = _disasterList

    fun getDisasterReports(timeperiod: String?,admin: String?,disaster: String?) {
        viewModelScope.launch {
            try {
                val disasters = useCase.getDisasterReports(timeperiod,admin, disaster)
                _disasterList.postValue(disasters)
                Log.d("DisasterDataViewModel", "Disaster Data Retrieved: ${disasters.result?.type ?: "nodata"}")
            } catch (e: Exception) {
                // Handle error here, e.g., show an error message to the user
            }
        }
    }

    fun getDisasterReportsByPeriod(start: String?,end: String?) {
        viewModelScope.launch {
            try {
                val disasters = useCase.getDisasterReportsByPeriod(start,end)
                _disasterList.postValue(disasters)
                Log.d("DisasterDataViewModel", "Disaster Data Retrieved: ${disasters.result?.type ?: "nodata"}")
            } catch (e: Exception) {
                // Handle error here, e.g., show an error message to the user
            }
        }
    }

}
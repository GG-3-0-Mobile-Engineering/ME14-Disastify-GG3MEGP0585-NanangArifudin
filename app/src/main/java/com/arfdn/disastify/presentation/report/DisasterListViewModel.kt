package com.arfdn.disastify.presentation.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arfdn.disastify.domain.model.Disaster
import com.arfdn.disastify.domain.usecase.DisasterUseCase
import kotlinx.coroutines.launch

class DisasterListViewModel(private val useCase: DisasterUseCase) : ViewModel() {
    private val _disasterList = MutableLiveData<List<Disaster>>()
    val disasterList: LiveData<List<Disaster>> get() = _disasterList




    fun getDisasterReports() {
        viewModelScope.launch {
            try {
                val disasters = useCase.getDisasterReports()
                _disasterList.postValue(disasters)
            } catch (e: Exception) {
                // Handle error here, e.g., show an error message to the user
            }
        }
    }
}
package com.qndev.taipeiexplorer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qndev.taipeiexplorer.domain.reposiroty.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.getLocations(1, "vi").collectLatest { }
        }
    }

    fun init() {}

}
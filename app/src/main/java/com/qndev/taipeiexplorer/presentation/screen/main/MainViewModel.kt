package com.qndev.taipeiexplorer.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qndev.taipeiexplorer.domain.usecase.GetLocationsUseCase
import com.qndev.taipeiexplorer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    private var _locationListState = MutableStateFlow(LocationListState())
    val locationListState = _locationListState.asStateFlow()

    fun getLocations() {
        viewModelScope.launch(Dispatchers.IO) {

            _locationListState.update { it.copy(isLoading = true) }

            val page = locationListState.value.locationListPage
            val language = locationListState.value.language

            getLocationsUseCase.invoke(page, language).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { locationList ->
                            _locationListState.update {
                                it.copy(
                                    locationList = locationListState.value.locationList + locationList,
                                    locationListPage = page + 1,
                                    errorMassage = ""
                                )
                            }
                        }

                    }

                    is Resource.Error -> {
                        result.message?.let { msg ->
                            _locationListState.update {
                                it.copy(errorMassage = msg)
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _locationListState.update { it.copy(isLoading = result.isLoading) }
                    }
                }
            }
        }
    }

    fun resetLocationListState() {
        _locationListState.update { it.copy(locationList = emptyList(), locationListPage = 1) }
    }

    fun updateLanguage(language: String) {
        _locationListState.update { it.copy(language = language) }
    }

}
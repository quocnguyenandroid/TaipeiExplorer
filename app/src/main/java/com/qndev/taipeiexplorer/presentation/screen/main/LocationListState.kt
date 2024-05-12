package com.qndev.taipeiexplorer.presentation.screen.main

import com.qndev.taipeiexplorer.domain.model.Location

data class LocationListState(
    val isLoading: Boolean = false,
    val locationListPage: Int = 1,
    val locationList: List<Location> = emptyList(),
    val errorMassage: String = "",
    val language: String = "en"
)

package com.qndev.taipeiexplorer.domain.usecase

import com.qndev.taipeiexplorer.domain.reposiroty.LocationRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend fun invoke(page: Int, lang: String) = locationRepository.getLocations(page, lang)
}
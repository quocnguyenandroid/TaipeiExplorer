package com.qndev.taipeiexplorer.domain.usecase

import com.qndev.taipeiexplorer.domain.model.Location
import com.qndev.taipeiexplorer.domain.reposiroty.LocationRepository
import com.qndev.taipeiexplorer.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend fun invoke(page: Int, lang: String): Flow<Resource<List<Location>>> =
        locationRepository.getLocations(page, lang)
}
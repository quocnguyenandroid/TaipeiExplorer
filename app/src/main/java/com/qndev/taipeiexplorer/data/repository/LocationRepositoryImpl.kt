package com.qndev.taipeiexplorer.data.repository

import com.qndev.taipeiexplorer.data.local.LocationDatabase
import com.qndev.taipeiexplorer.data.local.mapper.toLocationEntity
import com.qndev.taipeiexplorer.data.local.mapper.toLocationModel
import com.qndev.taipeiexplorer.data.remote.api.LocationApi
import com.qndev.taipeiexplorer.domain.model.Location
import com.qndev.taipeiexplorer.domain.reposiroty.LocationRepository
import com.qndev.taipeiexplorer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDatabase: LocationDatabase,
    private val locationApi: LocationApi
) : LocationRepository {

    override suspend fun getLocations(page: Int, lang: String): Flow<Resource<List<Location>>> {
        return flow {
            emit(Resource.Loading(true))

            val locations = try {
                locationApi.getLocationData(page = page, lang = lang)
            } catch (e: Exception) {
                e.printStackTrace()

                // Get data from local database when error from api
                val localLocationList = locationDatabase.locationDao.getLocations(page, lang)
                if (localLocationList.isEmpty()) {
                    emit(Resource.Error("No data to be shown"))
                } else {
                    emit(Resource.Success(data = localLocationList.map { it.toLocationModel() }))
                }
                emit(Resource.Loading(false))
                return@flow
            }

            // Map LocationDto to LocationEntity
            val locationEntities = locations.location.map { locationDto ->
                locationDto.toLocationEntity(page, lang)
            }

            // Save data to local database
            locationDatabase.locationDao.upsertLocations(locationEntities)

            emit(Resource.Success(data = locationEntities.map { it.toLocationModel() }))
            emit(Resource.Loading(false))
        }
    }
}
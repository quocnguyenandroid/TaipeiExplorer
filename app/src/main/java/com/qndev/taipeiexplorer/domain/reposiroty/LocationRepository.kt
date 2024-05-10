package com.qndev.taipeiexplorer.domain.reposiroty

import com.qndev.taipeiexplorer.domain.model.Location
import com.qndev.taipeiexplorer.util.Resource
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun getLocations(page: Int, lang: String): Flow<Resource<List<Location>>>

}
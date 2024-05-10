package com.qndev.taipeiexplorer.data.remote.api

import com.qndev.taipeiexplorer.data.remote.response.LocationListDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApi {

    @Headers("accept: application/json")
    @GET("{lang}/Attractions/All")
    suspend fun getLocationData(
        @Path("lang") lang: String = "en",
        @Query("page") page: Int = 1,
    ): LocationListDto

    companion object {
        const val BASE_URL = "https://www.travel.taipei/open-api/"
    }
}
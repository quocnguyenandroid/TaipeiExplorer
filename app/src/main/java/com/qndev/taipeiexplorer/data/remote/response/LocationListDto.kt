package com.qndev.taipeiexplorer.data.remote.response

import com.google.gson.annotations.SerializedName

data class LocationListDto(
    @SerializedName("data")
    val location: List<LocationDto>,
    val total: Int
)
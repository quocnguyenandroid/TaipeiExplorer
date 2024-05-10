package com.qndev.taipeiexplorer.domain.model

import com.qndev.taipeiexplorer.data.remote.response.CommonData
import com.qndev.taipeiexplorer.data.remote.response.ImageData

data class Location(

    val id: Int,

    val address: String,
    val category: List<CommonData>,
    val district: String,
    val elong: Double,
    val email: String,
    val facebook: String,
    val fax: String,
    val files: List<CommonData>,
    val friendly: List<CommonData>,
    val images: List<ImageData>,
    val introduction: String,
    val links: List<Any>,
    val modified: String,
    val months: String,
    val name: String,
    val nameZh: Any,
    val nlat: Double,
    val officialSite: String,
    val openStatus: Int,
    val openTime: String,
    val remind: String,
    val service: List<CommonData>,
    val staytime: String,
    val target: List<CommonData>,
    val tel: String,
    val ticket: String,
    val url: String,
    val zipcode: String,

    val page: Int,
    val lang: String,
)
package com.qndev.taipeiexplorer.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class LocationDto(
    val address: String?,
    val category: List<CommonData>?,
    val distric: String?,
    val elong: Double?,
    val email: String?,
    val facebook: String?,
    val fax: String?,
    val files: List<CommonData>?,
    val friendly: List<CommonData>?,
    val id: Int?,
    val images: List<ImageData>?,
    val introduction: String?,
    val links: List<CommonData>?,
    val modified: String?,
    val months: String?,
    val name: String?,
    val name_zh: String?,
    val nlat: Double?,
    val official_site: String?,
    val open_status: Int?,
    val open_time: String?,
    val remind: String?,
    val service: List<CommonData>?,
    val staytime: String?,
    val target: List<CommonData>?,
    val tel: String?,
    val ticket: String?,
    val url: String?,
    val zipcode: String?
)

@Parcelize
data class CommonData(
    val id: Int?,
    val name: String?
) : Parcelable

@Parcelize
data class ImageData(
    val src: String?,
    val subject: String?,
    val ext: String?
) : Parcelable
package com.qndev.taipeiexplorer.data.local

import androidx.room.Entity
import androidx.room.TypeConverters
import com.qndev.taipeiexplorer.data.local.mapper.LocationMapper

@Entity(primaryKeys = ["id", "lang"])
data class LocationEntity(

    val id: Int,
    val lang: String,

    val address: String,
    val category: List<CommonDataEntity>,
    val district: String,
    val elong: Double,
    val email: String,
    val facebook: String,
    val fax: String,
    val files: List<CommonDataEntity>,
    val friendly: List<CommonDataEntity>,
    val images: List<ImageDataEntity>,
    val introduction: String,
    val links: List<CommonDataEntity>,
    val modified: String,
    val months: String,
    val name: String,
    val nameZh: String,
    val nlat: Double,
    val officialSite: String,
    val openStatus: Int,
    val openTime: String,
    val remind: String,
    val service: List<CommonDataEntity>,
    val staytime: String,
    val target: List<CommonDataEntity>,
    val tel: String,
    val ticket: String,
    val url: String,
    val zipcode: String,

    val page: Int,
)

@TypeConverters(LocationMapper::class)
data class CommonDataEntity(
    val id: Int,
    val name: String
)

@TypeConverters(LocationMapper::class)
data class ImageDataEntity(
    val src: String,
    val subject: String,
    val ext: String
)


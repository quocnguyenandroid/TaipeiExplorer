package com.qndev.taipeiexplorer.data.local.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.qndev.taipeiexplorer.data.local.CommonDataEntity
import com.qndev.taipeiexplorer.data.local.ImageDataEntity
import com.qndev.taipeiexplorer.data.local.LocationEntity
import com.qndev.taipeiexplorer.data.remote.response.CommonData
import com.qndev.taipeiexplorer.data.remote.response.ImageData
import com.qndev.taipeiexplorer.data.remote.response.LocationDto
import com.qndev.taipeiexplorer.domain.model.Location


class LocationMapper {

    private val gson = Gson()

    @TypeConverter
    fun convertDataCommonListToJson(value: List<CommonDataEntity>): String = gson.toJson(value)

    @TypeConverter
    fun convertJsonToDataCommonList(value: String): List<CommonDataEntity> {
        val objectType = object : TypeToken<List<CommonDataEntity>>() {}.type
        return gson.fromJson(value, objectType)
    }

    @TypeConverter
    fun convertImageDataListToJson(value: List<ImageDataEntity>): String = gson.toJson(value)

    @TypeConverter
    fun convertJsonToImageDataList(value: String): List<ImageDataEntity> {
        val objectType = object : TypeToken<List<ImageDataEntity>>() {}.type
        return gson.fromJson(value, objectType)
    }
}

fun LocationDto.toLocationEntity(page: Int, lang: String): LocationEntity {
    return LocationEntity(
        id = id ?: -1,
        address = address ?: "",
        category = category?.toCommonDataEntityList() ?: emptyList(),
        district = distric ?: "",
        elong = elong ?: -1.0,
        email = email ?: "",
        facebook = facebook ?: "",
        fax = fax ?: "",
        files = files?.toCommonDataEntityList() ?: emptyList(),
        friendly = friendly?.toCommonDataEntityList() ?: emptyList(),
        images = images?.toImageDataEntityList() ?: emptyList(),
        introduction = introduction ?: "",
        links = links?.toCommonDataEntityList() ?: emptyList(),
        modified = modified ?: "",
        months = months ?: "",
        name = name ?: "",
        nameZh = name_zh ?: "",
        nlat = nlat ?: -1.0,
        officialSite = official_site ?: "",
        openStatus = open_status ?: -1,
        openTime = open_time ?: "",
        remind = remind ?: "",
        service = service?.toCommonDataEntityList() ?: emptyList(),
        staytime = staytime ?: "",
        target = target?.toCommonDataEntityList() ?: emptyList(),
        tel = tel ?: "",
        ticket = ticket ?: "",
        url = url ?: "",
        zipcode = zipcode ?: "",
        page = page,
        lang = lang
    )
}

fun LocationEntity.toLocationModel(): Location {
    return Location(
        id = id,
        address = address,
        category = category.toCommonDataList(),
        district = district,
        elong = elong,
        email = email,
        facebook = facebook,
        fax = fax,
        files = files.toCommonDataList(),
        friendly = friendly.toCommonDataList(),
        images = images.toImageDataList(),
        introduction = introduction,
        links = links,
        modified = modified,
        months = months,
        name = name,
        nameZh = nameZh,
        nlat = nlat,
        officialSite = officialSite,
        openStatus = openStatus,
        openTime = openTime,
        remind = remind,
        service = service.toCommonDataList(),
        staytime = staytime,
        target = target.toCommonDataList(),
        tel = tel,
        ticket = ticket,
        url = url,
        zipcode = zipcode,
        page = page,
        lang = lang,
    )
}

private fun List<CommonData>.toCommonDataEntityList(): List<CommonDataEntity> {
    return this.map {
        CommonDataEntity(
            id = it.id ?: -1,
            name = it.name ?: "",
        )
    }
}

private fun List<CommonDataEntity>.toCommonDataList(): List<CommonData> {
    return this.map {
        CommonData(
            id = it.id,
            name = it.name,
        )
    }
}

private fun List<ImageDataEntity>.toImageDataList(): List<ImageData> {
    return this.map {
        ImageData(
            src = it.src,
            subject = it.subject,
            ext = it.ext
        )
    }
}

private fun List<ImageData>.toImageDataEntityList(): List<ImageDataEntity> {
    return this.map {
        ImageDataEntity(
            src = it.src ?: "",
            subject = it.subject ?: "",
            ext = it.ext ?: ""
        )
    }
}
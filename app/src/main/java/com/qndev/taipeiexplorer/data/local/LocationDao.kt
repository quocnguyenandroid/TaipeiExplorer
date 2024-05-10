package com.qndev.taipeiexplorer.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface LocationDao {

    @Upsert
    suspend fun upsertLocations(locations: List<LocationEntity>)

    @Query("SELECT * FROM LocationEntity WHERE page = :page AND lang = :lang")
    suspend fun getLocations(page: Int, lang: String): List<LocationEntity>
}
package com.qndev.taipeiexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qndev.taipeiexplorer.data.local.mapper.LocationMapper


@Database(
    entities = [LocationEntity::class],
    version = 1,
)
@TypeConverters(LocationMapper::class)
abstract class LocationDatabase : RoomDatabase() {
    abstract val locationDao: LocationDao
}
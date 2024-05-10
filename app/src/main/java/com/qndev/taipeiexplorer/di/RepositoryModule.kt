package com.qndev.taipeiexplorer.di

import com.qndev.taipeiexplorer.data.repository.LocationRepositoryImpl
import com.qndev.taipeiexplorer.domain.reposiroty.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository
}
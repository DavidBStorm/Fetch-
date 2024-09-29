package com.test.fetch.di


import com.test.fetch.data.repository.FetchRepository
import com.test.fetch.data.repositoryImpl.FetchRepositoryImpl
import com.test.fetch.data.services.ListServices
import com.test.fetch.domain.usecase.FetchItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    @Singleton
    fun provideFetchRepository(
        listServices: ListServices
    ): FetchRepository = FetchRepositoryImpl(listServices)

    @Provides
    @Singleton
    fun provideFetchItemsUseCase(
        fetchRepository: FetchRepository
    ): FetchItemsUseCase = FetchItemsUseCase(fetchRepository)
}


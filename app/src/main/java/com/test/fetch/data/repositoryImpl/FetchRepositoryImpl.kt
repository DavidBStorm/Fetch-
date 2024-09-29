package com.test.fetch.data.repositoryImpl

import com.test.fetch.data.repository.FetchRepository
import com.test.fetch.data.services.ListServices

import com.test.fetch.domain.model.FetchItem
import javax.inject.Inject


    class FetchRepositoryImpl @Inject constructor(
        private val listServices: ListServices
    ) : FetchRepository {
        override suspend fun getFetchItems(): List<FetchItem> {
            return listServices.getFetchItems()
        }
    }


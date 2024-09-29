package com.test.fetch.data.repository

import com.test.fetch.domain.model.FetchItem

interface FetchRepository {
    suspend fun getFetchItems(): List<FetchItem>
}

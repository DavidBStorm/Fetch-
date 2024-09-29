package com.test.fetch.domain.usecase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.fetch.data.repository.FetchRepository
import com.test.fetch.domain.model.FetchItem
import javax.inject.Inject

class FetchItemsUseCase @Inject constructor(
    private val fetchRepository: FetchRepository
) {
    suspend operator fun invoke(): List<FetchItem> {
        return fetchRepository.getFetchItems()
    }
}


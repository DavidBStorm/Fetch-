package com.test.fetch.domain.usecase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.fetch.data.repository.FetchRepository
import com.test.fetch.domain.model.FetchItem

class FetchPagingSource(
    private val repository: FetchRepository
) : PagingSource<Int, FetchItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FetchItem> {
        return try {
            val data = repository.getFetchItems()
            LoadResult.Page(
                data = data.filter { !it.name.isNullOrBlank() }, // Filter blank names
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FetchItem>): Int? {
        return state.anchorPosition
    }
}

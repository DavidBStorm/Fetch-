package com.test.fetch.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.test.fetch.domain.model.FetchItem
import com.test.fetch.domain.usecase.FetchItemsUseCase
import com.test.fetch.presentation.state.FetchIntent
import com.test.fetch.presentation.state.FetchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FetchViewModel @Inject constructor(
    private val fetchItemsUseCase: FetchItemsUseCase
) : ViewModel() {

    // Pager to handle paging and sorting
    val fetchItemsPaging = Pager(PagingConfig(pageSize = 20)) {
        object : PagingSource<Int, FetchItem>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FetchItem> {
                return try {
                    // Fetch data via use case
                    val data = fetchItemsUseCase.invoke()
                        .filter { !it.name.isNullOrBlank() } // Filter blank or null names
                        .sortedWith(compareBy({ it.listId }, { it.name })) // Sort by listId, then by name

                    // Return the sorted, filtered data
                    LoadResult.Page(
                        data = data,
                        prevKey = null, // No previous page
                        nextKey = null  // No next page (all data loaded)
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e) // Handle errors
                }
            }

            override fun getRefreshKey(state: PagingState<Int, FetchItem>): Int? {
                return state.anchorPosition
            }
        }
    }.flow.cachedIn(viewModelScope)
}


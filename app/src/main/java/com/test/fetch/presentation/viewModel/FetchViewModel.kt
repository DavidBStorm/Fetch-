package com.test.fetch.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.test.fetch.domain.usecase.FetchItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchViewModel @Inject constructor(
    private val fetchItemsUseCase: FetchItemsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FetchState>(FetchState.Loading)
    val state: StateFlow<FetchState> = _state

    val fetchItems = Pager(PagingConfig(pageSize = 20)) {
        FetchPagingSource(fetchItemsUseCase)
    }.flow.cachedIn(viewModelScope)

    fun onEvent(intent: FetchIntent) {
        when (intent) {
            is FetchIntent.LoadItems -> {
                viewModelScope.launch {
                    _state.value = FetchState.Loading
                    try {
                        val items = fetchItemsUseCase.invoke().filter { !it.name.isNullOrBlank() }
                        _state.value = FetchState.Success(items)
                    } catch (e: Exception) {
                        _state.value = FetchState.Error(e.message ?: "Unknown error")
                    }
                }
            }
        }
    }
}

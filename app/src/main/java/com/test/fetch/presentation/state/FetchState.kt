package com.test.fetch.presentation.state

import com.test.fetch.domain.model.FetchItem

sealed class FetchState {
    data object Loading : FetchState()
    data class Success(val items: List<FetchItem>) : FetchState()
    data class Error(val message: String) : FetchState()
}

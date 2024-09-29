package com.test.fetch.presentation.state

sealed class FetchIntent {
    data object LoadItems : FetchIntent()
}

package com.test.fetch.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.test.fetch.domain.model.FetchItem
import com.test.fetch.presentation.viewModel.FetchViewModel

@Composable
fun FetchItemScreen(
    viewModel: FetchViewModel = hiltViewModel()
) {
    val fetchItems = viewModel.fetchItemsPaging.collectAsLazyPagingItems()

    // Handle different states (Loading, Error, and Data)
    when (fetchItems.loadState.refresh) {
        is LoadState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize()) // Show loading spinner
        }
        is LoadState.Error -> {
            val error = fetchItems.loadState.refresh as LoadState.Error
            Text(
                text = "Error: ${error.error.localizedMessage}",
                modifier = Modifier.fillMaxSize(),
                color = Color.Red
            )
        }
        else -> {
            LazyColumn {
                // Display paginated data in LazyColumn
                items(fetchItems.itemCount) { index ->
                    val item = fetchItems[index]
                    item?.let {
                        FetchItemView(item) // Pass each item to the Item View
                    }
                }

                // Handle loading at the end of the list
                fetchItems.apply {
                    when {
                        loadState.append is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val error = fetchItems.loadState.append as LoadState.Error
                            item {
                                Text(
                                    text = "Error loading more: ${error.error.localizedMessage}",
                                    color = Color.Red,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FetchItemView(item: FetchItem) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Name: ${item.name}")
        Text(text = "List ID: ${item.listId}")
    }
}


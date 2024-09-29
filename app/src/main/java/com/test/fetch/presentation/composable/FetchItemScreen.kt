package com.test.fetch.presentation.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.test.fetch.domain.model.FetchItem
import com.test.fetch.presentation.theme.Purple40
import com.test.fetch.presentation.theme.Purple80
import com.test.fetch.presentation.viewModel.FetchViewModel

@Composable
fun FetchItemScreen(viewModel: FetchViewModel = hiltViewModel(), innerPadding: PaddingValues) {
    val fetchItems = viewModel.fetchItemsPaging.collectAsLazyPagingItems()
    Box(
        Modifier
            .fillMaxSize()
            .background(Purple80)
            .padding(innerPadding)
    ) {
        when (fetchItems.loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is LoadState.Error -> {

                val error = fetchItems.loadState.refresh as LoadState.Error
                Text(
                    text = "Error: ${error.error.localizedMessage}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    color = Color.Red
                )
            }

            is LoadState.NotLoading -> {
                SetUpLazyColumn(fetchItems)
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetUpLazyColumn(fetchItems: LazyPagingItems<FetchItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Group items by listId
        val groupedItems = fetchItems.itemSnapshotList.items.groupBy { it.listId }

        groupedItems.forEach { (listId, items) ->
            // Display listId as a header
            stickyHeader {
                Text(
                    text = "List ID: $listId",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .background(Purple40)
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

            // Display items under the listId
            items(items) { item ->
                FetchItemView(item)
            }
        }
    }
}

@Composable
fun FetchItemView(item: FetchItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
    ) {
        Text(
            text = "Name: ${item.name}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = "List ID: ${item.listId}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}



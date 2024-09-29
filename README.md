
# Fetch Items App

This project demonstrates fetching, displaying, and organizing a list of items from an API using **Jetpack Compose**, **Paging 3**, and the **MVI** architecture pattern. The data is grouped by `listId`, sorted by `listId` and `name`, and filtered to exclude items with blank or null names. 

## Features:
- **Clean Architecture**: Separation of concerns with `UseCases`, `Repositories`, and `ViewModel`.
- **Jetpack Compose**: UI components are built using the declarative Compose framework.
- **Paging 3**: Efficiently handles large datasets by loading items in chunks (paging).
- **MVI Architecture**: Model-View-Intent (MVI) pattern is used for state management.
- **Sticky Headers**: Grouped items are displayed with headers that stick to the top while scrolling.

## Requirements
- Kotlin
- Android Studio Arctic Fox (or later)
- Paging 3 library
- Jetpack Compose
- Retrofit
- Hilt for dependency injection

## API Used
Data is fetched from the following API:

```
https://fetch-hiring.s3.amazonaws.com/hiring.json
```

### API Data Structure:

```json
[
  {
    "id": 1,
    "listId": 1,
    "name": "Item 1"
  },
  {
    "id": 2,
    "listId": 1,
    "name": "Item 2"
  },
  {
    "id": 3,
    "listId": 2,
    "name": "Item 3"
  }
]
```

## Key Components

### **1. ViewModel (`FetchViewModel`)**
- **Purpose**: Manages fetching and transforming data for the UI.
- **Paging Source**: Uses a `Pager` to load items in pages.
- **Business Logic**: Filters out items with blank or null names and sorts them by `listId` and `name`.

```kotlin
val fetchItemsPaging = Pager(PagingConfig(pageSize = 20)) {
    object : PagingSource<Int, FetchItem>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FetchItem> {
            return try {
                val data = fetchItemsUseCase.invoke()
                    .filter { !it.name.isNullOrBlank() }
                    .sortedWith(compareBy({ it.listId }, { it.name }))

                LoadResult.Page(data = data, prevKey = null, nextKey = null)
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }
}.flow.cachedIn(viewModelScope)
```

### **2. UI (Jetpack Compose)**
- **LazyColumn**: Displays the fetched data with each `listId` acting as a header.
- **StickyHeader**: Headers "stick" to the top as you scroll through the items.
- **Progress Indicator**: Displays a loading spinner while fetching data.

```kotlin
LazyColumn {
    val groupedItems = fetchItems.itemSnapshotList.items.groupBy { it.listId }

    groupedItems.forEach { (listId, items) ->
        stickyHeader {
            Text(
                text = "List ID: $listId",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        items(items) { item ->
            FetchItemView(item)
        }
    }
}
```

### **3. FetchItemView**
- **Purpose**: Displays individual item details such as the name and list ID.

```kotlin
@Composable
fun FetchItemView(item: FetchItem) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Name: ${item.name}")
        Text(text = "List ID: ${item.listId}")
    }
}
```

## Dependency Injection (Hilt)
- **MyApplicationModule**: Provides application-level dependencies like `Retrofit` and API services.
- **AppModules**: Contains dependencies like `Repositories` and `UseCases`.

## Paging Setup
- **Paging 3**: Used for handling large datasets.
- **Data Loading**: Handled in `ViewModel` using `Pager` and `PagingSource`.
- **UI Integration**: `LazyPagingItems` is used to connect the data flow between the ViewModel and the UI.

## How to Run
1. Clone this repository.
2. Open the project in Android Studio.
3. Sync Gradle.
4. Run the app on an emulator or device.

## Future Improvements
- **Error Handling**: More detailed error messages can be displayed.
- **Unit Testing**: Add unit tests for the `ViewModel`, `UseCase`, and UI components.
- **UI Enhancement**: Further styling and animations for a better user experience.

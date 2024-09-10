package com.winteryy.readit.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.winteryy.readit.model.Book

@Composable
fun BookListColumn(
    lazyPagingBooks: LazyPagingItems<Book>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onItemClicked: (Book) -> Unit = {}
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        items(
            count = lazyPagingBooks.itemCount,
            key = lazyPagingBooks.itemKey(key = { book -> book.isbn }),
            contentType = lazyPagingBooks.itemContentType()
        ) { ind ->
            val item = lazyPagingBooks[ind]
            item?.let {
                BookItemRow(book = it, onClick = onItemClicked)
            }
            if(ind!=lazyPagingBooks.itemCount-1) {
                HorizontalDivider()
            }
        }
    }
}

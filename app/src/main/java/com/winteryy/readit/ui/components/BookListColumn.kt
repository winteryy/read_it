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
import com.winteryy.readit.model.Comment

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

@Composable
fun BookWithCommentListColumn(
    lazyPagingBookWithComments: LazyPagingItems<Pair<Comment, Book>>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onItemClicked: (Pair<Comment, Book>) -> Unit = {}
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        items(
            count = lazyPagingBookWithComments.itemCount,
            key = lazyPagingBookWithComments.itemKey(key = { bookWithComment -> bookWithComment.second.isbn }),
            contentType = lazyPagingBookWithComments.itemContentType()
        ) { ind ->
            val item = lazyPagingBookWithComments[ind]
            item?.let {
                BookWithCommentItemRow(book = it.second, comment = it.first, onClick = onItemClicked)
            }
            if(ind!=lazyPagingBookWithComments.itemCount-1) {
                HorizontalDivider()
            }
        }
    }
}

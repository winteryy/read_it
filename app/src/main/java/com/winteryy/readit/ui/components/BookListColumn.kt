package com.winteryy.readit.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.theme.ReadItTheme
import java.util.Date

@Composable
fun BookListColumn(
    bookList: List<Book>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onItemClicked: (Book) -> Unit = {}
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        itemsIndexed(
            items = bookList,
            key = { _, book -> book.isbn }
        ) { ind, book ->
            BookItemRow(
                book = book,
                onClick = onItemClicked
            )
            if(ind!=bookList.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookListColumnPreview() {
    val state = rememberLazyListState()
    ReadItTheme {
        BookListColumn(
            bookList = listOf(
                Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "1321412",
                    description = "asdasd",
                    pubDate = Date(),
                ),
                Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "13214130",
                    description = "asdasd",
                    pubDate = Date(),
                ),
                Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "13214129",
                    description = "asdasd",
                    pubDate = Date(),
                ),
                Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "13214128",
                    description = "asdasd",
                    pubDate = Date(),
                ),
                Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "13214127",
                    description = "asdasd",
                    pubDate = Date(),
                ),
                Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "13214126",
                    description = "asdasd",
                    pubDate = Date(),
                ),
                Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "13214125",
                    description = "asdasd",
                    pubDate = Date(),
                ),
                Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "13214124",
                    description = "asdasd",
                    pubDate = Date(),
                ),
                Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "13214121",
                    description = "asdasd",
                    pubDate = Date(),
                ),
                Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "13214122",
                    description = "asdasd",
                    pubDate = Date(),
                ),
                Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "13214123",
                    description = "asdasd",
                    pubDate = Date(),
                )
            ),
            lazyListState = state) {

        }
    }
}
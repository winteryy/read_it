package com.winteryy.readit.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import com.winteryy.readit.ui.theme.ReadItTheme
import java.util.Date

@Composable
fun SectionItem(
    section: Section,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        val lazyRowState = rememberLazyListState()

        Text(
            text = section.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .paddingFromBaseline(bottom = 16.dp)
        )
        LazyRow(state = lazyRowState) {
            itemsIndexed(
                items = section.bookList,
                key = { _, book -> book.isbn }
            ) { ind, book ->
                if(ind!=0) Spacer(modifier = Modifier.size(8.dp))
                BookItem(book = book)
            }
        }
    }
}

@Preview
@Composable
fun PreviewSectionItem() {
    ReadItTheme {
        SectionItem(section = Section(
            "읽는 중인 책",
            listOf(
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
            )
        )
        )
    }
}
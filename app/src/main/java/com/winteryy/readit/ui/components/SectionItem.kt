package com.winteryy.readit.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import com.winteryy.readit.model.SectionType
import com.winteryy.readit.ui.theme.ReadItTheme
import java.util.Date

@Composable
fun SectionItem(
    section: Section,
    modifier: Modifier = Modifier,
    onArrowClicked: (Section) -> Unit = {},
    onItemClicked: (Book) -> Unit = {}
) {
    Column(modifier) {
        val lazyRowState = rememberLazyListState()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = section.sectionType.title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "show all button",
                modifier = Modifier.clickable {
                    onArrowClicked(section)
                }
            )
        }
        LazyRow(state = lazyRowState) {
            itemsIndexed(
                items = section.bookList,
                key = { _, book -> book.isbn }
            ) { ind, book ->
                if(ind!=0) Spacer(modifier = Modifier.size(8.dp))
                BookItem(
                    book = book,
                    onClick = onItemClicked
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SectionItemPreview() {
    ReadItTheme {
        SectionItem(
            section = Section(
                sectionType = SectionType.READING,
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
                )
            )
        )
    }
}
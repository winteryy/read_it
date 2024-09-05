package com.winteryy.readit.ui.bookdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.BookSaveStatus
import com.winteryy.readit.ui.components.BookItemRow
import com.winteryy.readit.ui.components.TextTopBar
import com.winteryy.readit.ui.theme.ReadItTheme
import java.util.Date

@Composable
fun BookDetailScreen(
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White) //todo 완성 후 필요 체크
    ) {
        val scrollState = rememberScrollState()

        TextTopBar(
            title = "책 상세 정보"
        )
        HorizontalDivider()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .background(Color.LightGray)
        ) {
            BookItemRow(book = book)
            Spacer(modifier = Modifier.size(8.dp))
            BookActionPanel(
                2.5f,
                BookSaveStatus.WISH,
                false
            )

        }

    }
}

@Composable
@Preview(showBackground = true)
fun BookDetailScreenPreview() {
    ReadItTheme {
        BookDetailScreen(
            book = Book(
                "정의란 무엇인가",
                "https://picsum.photos/300/400",
                "test author",
                "test publisher",
                "1321412",
                description = "asdasd",
                pubDate = Date(),
            )
        )
    }
}
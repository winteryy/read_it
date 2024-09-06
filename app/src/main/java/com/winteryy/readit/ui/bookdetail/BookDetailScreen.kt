package com.winteryy.readit.ui.bookdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.R
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.BookSaveStatus
import com.winteryy.readit.ui.components.BookItemRow
import com.winteryy.readit.ui.components.TextTopBar
import com.winteryy.readit.ui.components.TitleAndText
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke
import java.util.Date

@Composable
fun BookDetailScreen(
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        val scrollState = rememberScrollState()

        TextTopBar(
            title = stringResource(R.string.book_detail_title)
        )
        HorizontalDivider()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(theme_grey_whiteSmoke)
        ) {
            BookItemRow(book = book)
            Spacer(modifier = Modifier.size(12.dp))
            BookActionPanel(
                2.5f,
                BookSaveStatus.WISH,
                false
            )
            Spacer(modifier = Modifier.size(12.dp))
            TitleAndText(
                title = stringResource(R.string.book_description_title),
                text = book.description
            )
            Spacer(modifier = Modifier.size(12.dp))
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
                description = "명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 책 설명 텍스트 ",
                pubDate = Date(),
            )
        )
    }
}
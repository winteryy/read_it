package com.winteryy.readit.ui.bookdetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.theme.ReadItTheme
import java.util.Date

@Composable
fun BookDetailScreen(
    book: Book,
    modifier: Modifier = Modifier
) {

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
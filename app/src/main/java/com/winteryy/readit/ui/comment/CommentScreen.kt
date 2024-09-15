package com.winteryy.readit.ui.comment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.theme.ReadItTheme
import java.util.Date

@Composable
fun CommentScreen() {
    val bookList = listOf(
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
            "1321412",
            description = "asdasd",
            pubDate = Date(),
        )
    )

    Column() {
        Text("최근에 작성한 코멘트")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(
                count = bookList.size,
            ) { ind ->
                CommentItem(book = bookList[ind])
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun CommentScreenPreview() {
    ReadItTheme {
        CommentScreen()
    }
}
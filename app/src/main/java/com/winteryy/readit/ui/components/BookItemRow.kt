package com.winteryy.readit.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography
import com.winteryy.readit.ui.theme.theme_grey_gunPowder
import com.winteryy.readit.ui.util.getYearByCalender
import java.util.Date

private val BOOK_HEIGHT = 150.dp

@Composable
fun BookItemRow(
    book: Book,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = book.image,
            contentDescription = book.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp, BOOK_HEIGHT)
                .clip(RoundedCornerShape(10.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(BOOK_HEIGHT)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = book.title,
                style = Typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(
                text = book.author,
                style = Typography.bodyMedium
            )
            Text(
                text = book.pubDate.getYearByCalender().toString() + " · " + book.publisher,
                style = Typography.bodyMedium.copy(color = theme_grey_gunPowder)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookItemRowPreview() {
    ReadItTheme {
        BookItemRow(
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
package com.winteryy.readit.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import java.util.Date

@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    book: Book
) {
    Column(
        modifier = modifier
            .width(98.dp)
    ) {
        AsyncImage(
            model = book.image,
            contentDescription = book.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(98.dp, 136.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = book.title,
            modifier = Modifier
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            minLines = 2,
            maxLines = 2,
        )
    }
}

@Preview
@Composable
fun PreviewBookItem() {
    ReadItTheme {
        BookItem(
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
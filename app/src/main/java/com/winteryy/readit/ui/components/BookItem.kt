package com.winteryy.readit.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.theme_color_lightDodgerBlue
import java.util.Date

private val BOOK_WIDTH = 140.dp

@Composable
fun BookItem(
    book: Book,
    modifier: Modifier = Modifier,
    bookWidth: Dp = BOOK_WIDTH,
    showRating: Boolean = false,
    onClick: ((Book) -> Unit)? = null
) {
    Column(
        modifier = modifier
            .width(bookWidth)
            .let { originModifier ->
                onClick?.let { action ->
                    originModifier.clickable {
                        action(book)
                    }
                } ?: originModifier
            }
    ) {
        AsyncImage(
            model = book.image,
            contentDescription = book.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(bookWidth, bookWidth/2*3)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = book.title,
            modifier = Modifier
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            style = typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            minLines = 2,
            maxLines = 2,
        )
        if(showRating) {
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "★ ${book.rating}",
                style = typography.bodySmall,
                color = theme_color_lightDodgerBlue,
            )
        }
    }
}

@Preview
@Composable
fun PreviewBookItem() {
    ReadItTheme {
        BookItem(
            book = Book(
                "정의란 무엇인가 긴거 확인긴거확인",
                "https://picsum.photos/300/400",
                "test author",
                "test publisher",
                "1321412",
                description = "asdasd",
                rating = 4.5f,
                pubDate = Date(),
            ),
            showRating = true
        )
    }
}
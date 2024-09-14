package com.winteryy.readit.ui.comment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.components.BookItem
import com.winteryy.readit.ui.theme.DEFAULT_PADDING
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography
import com.winteryy.readit.ui.theme.theme_color_lightDodgerBlue
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke
import java.util.Date

@Composable
fun CommentItem(
    book: Book,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp)),
        color = theme_grey_whiteSmoke
    ) {
        Layout(
            content = {
                BookItem(
                    book = book
                )
                Column {
                    Text(
                        text = "for testfor testfor testfor testfor testfor testfor testfor testforfor testfor testfor testfor testfor testfor testfor testfor testforfor testfor testfor testfor testfor testfor testfor testfor testforfor testfor testfor testfor testfor testfor testfor testfor testfor testfor test",
                        style = Typography.labelMedium,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f),
                    )
                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )
                    Text(
                        text = "24.09.23",
                        style = Typography.labelMedium,
                        color = theme_color_lightDodgerBlue,
                        textAlign = TextAlign.Right,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            },
            modifier = Modifier
                .padding(DEFAULT_PADDING)
        ) { measurables , constraints ->
            val bookItem = measurables[0]
            val bookItemPlaceable = bookItem.measure(constraints)

            val maxHeight = bookItemPlaceable.height

            val column = measurables[1]
            val columnPlaceable = column.measure(
                constraints.copy(
                    maxHeight = maxHeight,
                    maxWidth = constraints.maxWidth-bookItemPlaceable.width-8.dp.roundToPx()
                )
            )

            layout(constraints.maxWidth, maxHeight) {
                bookItemPlaceable.place(0, 0)
                columnPlaceable.place(bookItemPlaceable.width + 8.dp.roundToPx(), 0)
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun CommentItemPreview() {
    ReadItTheme {
        CommentItem(
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
package com.winteryy.readit.ui.comment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Comment
import com.winteryy.readit.ui.components.BookItem
import com.winteryy.readit.ui.theme.DEFAULT_PADDING
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography
import com.winteryy.readit.ui.theme.theme_color_lightDodgerBlue
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke
import com.winteryy.readit.ui.util.toReviewDateText
import java.util.Date

@Composable
fun CommentItem(
    book: Book,
    comment: Comment,
    onRecentCommentClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .width(360.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onRecentCommentClicked(book.isbn) },
        color = theme_grey_whiteSmoke
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(DEFAULT_PADDING)
        ) {
            val (bookItem, contentText, dateText) = createRefs()

            BookItem(
                book = book,
                modifier = Modifier
                    .constrainAs(bookItem) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )
            Text(
                text = comment.content,
                style = Typography.labelMedium,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                modifier = Modifier
                    .constrainAs(contentText) {
                        top.linkTo(parent.top)
                        start.linkTo(bookItem.end, margin = 8.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(dateText.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )
            Text(
                text = comment.updateDate.toReviewDateText(),
                style = Typography.labelMedium,
                color = theme_color_lightDodgerBlue,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .constrainAs(dateText) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )
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
            ),
            comment = Comment(
                1,
                "내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. ",
                Date(),
                "123214125215"
            ),
            onRecentCommentClicked = {}
        )
    }
}
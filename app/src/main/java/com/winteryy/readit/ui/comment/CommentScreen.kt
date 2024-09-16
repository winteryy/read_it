package com.winteryy.readit.ui.comment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.components.BookListColumn
import com.winteryy.readit.ui.components.TextTopBar
import com.winteryy.readit.ui.theme.DEFAULT_PADDING
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography
import com.winteryy.readit.ui.theme.theme_color_lightDodgerBlue
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentMainScreen() {
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

    val pagerState = rememberPagerState() {
        2
    }

    Column(
        modifier = Modifier
            .padding(DEFAULT_PADDING)
    ) {
        Text(
            text = "최근에 작성한 코멘트",
            style = Typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(top = DEFAULT_PADDING),
        ) { page ->
            CommentItem(
                book = bookList[page],
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = DEFAULT_PADDING)
                .clip(RoundedCornerShape(10.dp)),
            color = theme_grey_whiteSmoke
        ) {
            Text(
                text = buildAnnotatedString {
                    append("총 ")
                    withStyle(
                        style = SpanStyle(
                            color = theme_color_lightDodgerBlue
                        )
                    ) {
                        append("24")
                    }
                    append("권의 책에 코멘트를 작성했어요!")
                },
                style = Typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(DEFAULT_PADDING)
            )

        }
    }
}

@Composable
fun CommentListScreen() {
    TextTopBar(
        title = "코멘트를 작성한 책"
    )
//    BookListColumn(
//        lazyPagingBooks = ,
//        lazyListState =
//    )
}

@Composable
@Preview(showBackground = true)
fun CommentMainScreenPreview() {
    ReadItTheme {
        CommentMainScreen()
    }
}

@Composable
@Preview(showBackground = true)
fun CommentListScreenPreview() {
    ReadItTheme {
        CommentListScreen()
    }
}
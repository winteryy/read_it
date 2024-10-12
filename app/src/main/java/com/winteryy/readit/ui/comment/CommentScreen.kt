package com.winteryy.readit.ui.comment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Comment
import com.winteryy.readit.ui.components.BookListColumn
import com.winteryy.readit.ui.components.TextTopBar
import com.winteryy.readit.ui.theme.DEFAULT_PADDING
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography
import com.winteryy.readit.ui.theme.theme_color_lightDodgerBlue
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke
import kotlinx.coroutines.flow.Flow
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentMainScreen(
    commentNum: Int,
    curPage: Int,
    recentCommentList: List<Pair<Comment, Book>>,
    navigateToCommentList: () -> Unit,
    onCommentItemClicked: (String) -> Unit,
    onPageChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    //todo uiState 변화 및 composition에 따른 State 초기화 문제
    val pagerState = rememberPagerState(initialPage = curPage) { recentCommentList.size }

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(DEFAULT_PADDING)
    ) {
        if(recentCommentList.isNotEmpty()) {
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
                    book = recentCommentList[page].second,
                    comment = recentCommentList[page].first,
                    onRecentCommentClicked = onCommentItemClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = DEFAULT_PADDING),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iter ->
                    val color = if(pagerState.currentPage == iter) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = DEFAULT_PADDING)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { navigateToCommentList() },
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
                            append(commentNum.toString())
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
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "아직 작성한 코멘트가 없네요!\n읽은 책에 대한 코멘트를 작성해보세요.",
                    textAlign = TextAlign.Center
                )
            }

        }

    }
}

@Composable
fun CommentListScreen(
    booksHavingCommentPagingDataFlow: Flow<PagingData<Book>>,
    navigateToCommentMain: () -> Unit,
    onCommentItemClicked: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyPagingBooksHavingComment = booksHavingCommentPagingDataFlow.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

    Column(
        modifier = modifier
    ) {
        TextTopBar(
            title = "코멘트를 작성한 책",
            onBackArrowClicked = navigateToCommentMain
        )
        BookListColumn(
            lazyPagingBooks = lazyPagingBooksHavingComment,
            lazyListState = lazyListState,
            onItemClicked = onCommentItemClicked
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CommentMainScreenPreview() {
    ReadItTheme {
        CommentMainScreen(
            commentNum = 1,
            recentCommentList = listOf(
                Comment(
                    1,
                    "내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. 내용 확인용 텍스트입니다. ",
                    Date(),
                    "123214125215"
                ) to Book(
                    "정의란 무엇인가",
                    "https://picsum.photos/300/400",
                    "test author",
                    "test publisher",
                    "1321412",
                    description = "asdasd",
                    pubDate = Date(),
                )
            ),
            onCommentItemClicked = {},
            navigateToCommentList = {},
            onPageChanged = {},
            curPage = 0
        )
    }
}

//@Composable
//@Preview(showBackground = true)
//fun CommentListScreenPreview() {
//    ReadItTheme {
//        CommentListScreen()
//    }
//}
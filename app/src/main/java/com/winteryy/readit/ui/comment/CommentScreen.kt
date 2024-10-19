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
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.components.BookListColumn
import com.winteryy.readit.ui.components.IndeterminateCircularIndicator
import com.winteryy.readit.ui.components.TextTopBar
import com.winteryy.readit.ui.components.dialog.CustomDialog
import com.winteryy.readit.ui.components.dialog.DialogButtonInfo
import com.winteryy.readit.ui.components.dialog.DialogButtonType
import com.winteryy.readit.ui.theme.DEFAULT_PADDING
import com.winteryy.readit.ui.theme.Typography
import com.winteryy.readit.ui.theme.theme_color_lightDodgerBlue
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentMainScreen(
    commentMainState: CommentUiState.CommentMainState,
    snackbarHostState: SnackbarHostState,
    consumeErrorMessage: () -> Unit,
    navigateToCommentList: () -> Unit,
    onCommentItemClicked: (String) -> Unit,
    onPageChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = commentMainState.curPage) { commentMainState.recentCommentWithBookList.size }

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    if(commentMainState.errorMessage!=null) {
        LaunchedEffect(commentMainState.errorMessage) {
            snackbarHostState.showSnackbar(commentMainState.errorMessage)
            consumeErrorMessage()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(DEFAULT_PADDING)
    ) {
        if(commentMainState.isLoading) {
            IndeterminateCircularIndicator()
        } else {
            if(commentMainState.recentCommentWithBookList.isNotEmpty()) {
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
                        book = commentMainState.recentCommentWithBookList[page].second,
                        comment = commentMainState.recentCommentWithBookList[page].first,
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
                                append(commentMainState.commentNum.toString())
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
}

@Composable
fun CommentListScreen(
    commentListState: CommentUiState.CommentListState,
    navigateToCommentMain: () -> Unit,
    onCommentItemClicked: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    if(commentListState.errorMessage!=null) {
        CustomDialog(
            description = "코멘트 리스트를 불러오는 도중\n오류가 발생했습니다.",
            buttons = listOf(
                DialogButtonInfo(
                    text = "확인",
                    type = DialogButtonType.FILLED
                ) { navigateToCommentMain() }
            )
        )
    } else {
        val lazyPagingBooksHavingComment = commentListState.booksHavingCommentPagingDataFlow.collectAsLazyPagingItems()
        val lazyListState = rememberLazyListState()

        if(commentListState.isLoading) {
            IndeterminateCircularIndicator()
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
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
    }
}
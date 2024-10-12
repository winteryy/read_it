package com.winteryy.readit.ui.comment

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CommentRoute(
    navigateToEditComment: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val commentViewModel: CommentViewModel = hiltViewModel()
    val commentUiState = commentViewModel.commentUiState.collectAsStateWithLifecycle()

    commentUiState.value.let { curState ->
        println(curState)
        when(curState) {
            is CommentUiState.CommentListState -> {
                CommentListScreen(
                    booksHavingCommentPagingDataFlow = curState.booksHavingCommentPagingDataFlow,
                    navigateToCommentMain = { commentViewModel.setCommentMainState() },
                    onCommentItemClicked = { navigateToEditComment(it.isbn) },
                    modifier = modifier
                )
            }
            is CommentUiState.CommentMainState -> {
                CommentMainScreen(
                    commentNum = curState.commentNum,
                    curPage = curState.curPage,
                    recentCommentList = curState.recentCommentWithBookList,
                    navigateToCommentList = { commentViewModel.setCommentListState() },
                    onCommentItemClicked = { navigateToEditComment(it) },
                    onPageChanged = { commentViewModel.updateCurPage(it) },
                    modifier = modifier,
                )
            }
            CommentUiState.FailState -> {
                Text(text = "Fail State")
            }
            CommentUiState.Loading -> {
                Text(text = "Loading State")
            }
        }
    }

}
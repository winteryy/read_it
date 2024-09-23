package com.winteryy.readit.ui.comment

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CommentRoute(
    modifier: Modifier = Modifier
) {
    val commentViewModel: CommentViewModel = hiltViewModel()
    val commentUiState = commentViewModel.commentUiState.collectAsStateWithLifecycle()

    when(commentUiState.value) {
        is CommentUiState.CommentListState -> {
            CommentListScreen()
        }
        is CommentUiState.CommentMainState -> {
            CommentMainScreen(0, emptyList())
        }
        CommentUiState.FailState -> {
            Text(text = "Fail State")
        }
        CommentUiState.Loading -> {
            Text(text = "Loading State")
        }
    }
}
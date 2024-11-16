package com.winteryy.readit.ui.bookdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.winteryy.readit.R
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.components.BookItemRow
import com.winteryy.readit.ui.components.IndeterminateCircularIndicator
import com.winteryy.readit.ui.components.TextTopBar
import com.winteryy.readit.ui.components.TitleAndText
import com.winteryy.readit.ui.components.dialog.CustomDialog
import com.winteryy.readit.ui.components.dialog.DialogButtonInfo
import com.winteryy.readit.ui.components.dialog.DialogButtonType
import com.winteryy.readit.ui.theme.theme_grey_black
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke

@Composable
fun BookDetailScreen(
    book: Book?,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onBackArrowClicked: () -> Unit = {},
    onCommentButtonClicked: (String) -> Unit = {}
) {
    val bookDetailViewModel: BookDetailViewModel = hiltViewModel()
    val bookDetailUiState by bookDetailViewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        bookDetailViewModel.initBook(book)
    }

    bookDetailUiState.error?.let {
        when (it) {
            BookDetailUiError.InitFailError -> {
                CustomDialog(
                    description = stringResource(R.string.dialog_description_book_load_fail),
                    buttons = listOf(
                        DialogButtonInfo(
                            text = stringResource(R.string.confirm),
                            type = DialogButtonType.FILLED,
                        ) { onBackArrowClicked() }
                    )
                )
            }

            is BookDetailUiError.QueryError -> {
                LaunchedEffect(it) {
                    snackbarHostState.showSnackbar(it.msg)
                    bookDetailViewModel.consumeError()
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .matchParentSize()
        ) {
            TextTopBar(
                title = stringResource(R.string.book_detail_title),
                onBackArrowClicked = onBackArrowClicked
            )
            HorizontalDivider()
            if (bookDetailUiState.book != Book.NONE) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .background(theme_grey_whiteSmoke)
                ) {
                    BookItemRow(book = bookDetailUiState.book)
                    Spacer(modifier = Modifier.size(12.dp))
                    BookActionPanel(
                        bookDetailUiState.book.rating,
                        bookDetailUiState.book.bookSaveStatus,
                        bookDetailUiState.hasComment,
                        onRatingChanged = { bookDetailViewModel.setRating(it) },
                        onWishButtonClicked = { bookDetailViewModel.toggleWishBook() },
                        onReadingButtonClicked = { bookDetailViewModel.toggleReadingBook() },
                        onCommentButtonClicked = {
                            bookDetailViewModel.insertBeforeComment()
                            onCommentButtonClicked(bookDetailUiState.book.isbn)
                        }
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    TitleAndText(
                        title = stringResource(R.string.book_description_title),
                        text = bookDetailUiState.book.description
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                }
            }
        }

        if (bookDetailUiState.isLoading) {
            IndeterminateCircularIndicator(
                modifier = Modifier
                    .matchParentSize()
                    .background(color = theme_grey_black.copy(alpha = 0.5f))
            )
        }
    }


}

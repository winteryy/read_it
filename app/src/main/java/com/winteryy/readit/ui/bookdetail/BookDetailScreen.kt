package com.winteryy.readit.ui.bookdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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
import com.winteryy.readit.ui.components.TextTopBar
import com.winteryy.readit.ui.components.TitleAndText
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke

@Composable
fun BookDetailScreen(
    book: Book?,
    modifier: Modifier = Modifier,
    onBackArrowClicked: () -> Unit = {}
) {
    val bookDetailViewModel: BookDetailViewModel = hiltViewModel()
    val bookDetailUiState by bookDetailViewModel.bookState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        bookDetailViewModel.initBook(book)
    }

    bookDetailUiState.let { state ->
        when(state) {
            is BookDetailUiState.Fail -> { Text("fail") }
            BookDetailUiState.Loading -> { Text("loading") }
            is BookDetailUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    val scrollState = rememberScrollState()

                    TextTopBar(
                        title = stringResource(R.string.book_detail_title),
                        onBackArrowClicked =onBackArrowClicked
                    )
                    HorizontalDivider()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .background(theme_grey_whiteSmoke)
                    ) {
                        BookItemRow(book = state.book)
                        Spacer(modifier = Modifier.size(12.dp))
                        BookActionPanel(
                            state.book.rating,
                            state.book.bookSaveStatus,
                            false
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        TitleAndText(
                            title = stringResource(R.string.book_description_title),
                            text = state.book.description
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                    }

                }
            }
        }
    }

}

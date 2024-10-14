package com.winteryy.readit.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.winteryy.readit.model.Book
import com.winteryy.readit.ui.components.IndeterminateCircularIndicator

@Composable
fun HomeRoute(
    navigateToBookDetail: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val sectionLazyListState = rememberLazyListState()
    val homeUiState = homeViewModel.homeUiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
    ) {

        homeUiState.value.let { curState ->
            HomeTopBar(
                homeTopBarType = when(curState) {
                    is HomeUiState.FeedState -> HomeTopBarType.SearchBar.Default
                    is HomeUiState.SearchState -> HomeTopBarType.SearchBar.Searching
                    is HomeUiState.SearchResultState -> HomeTopBarType.SearchBar.Searching
                    is HomeUiState.SectionDetailState -> HomeTopBarType.TextBar(
                        title = curState.sectionType.title
                    )
                },
                onTextInputTriggered = { homeViewModel.setSearchingScreen() },
                onBackArrowClicked = { homeViewModel.setFeedScreen() },
                onSearch = {
                    homeViewModel.setSearchResultScreen(it)
                }
            )

            HorizontalDivider()

            if(curState.isLoading) {
                IndeterminateCircularIndicator()
            } else {
                when(curState) {
                    is HomeUiState.FeedState -> {
                        HomeFeedScreen(
                            sectionList = curState.sectionList,
                            sectionLazyListState = sectionLazyListState,
                            onSectionArrowClicked = { homeViewModel.setSectionDetailScreen(it) },
                            onSectionItemClicked = navigateToBookDetail
                        )
                    }
                    is HomeUiState.SearchState -> HomeSearchScreen()
                    is HomeUiState.SearchResultState -> {
                        HomeSearchResultScreen(
                            bookPagingDataFlow = curState.bookPagingDataFlow,
                            onResultItemClicked = navigateToBookDetail
                        )
                    }
                    is HomeUiState.SectionDetailState -> HomeSectionDetailScreen(
                        bookPagingDataFlow = curState.sectionBookPagingDataFlow,
                        onBookItemClicked = navigateToBookDetail
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeRoutePreview() {
}
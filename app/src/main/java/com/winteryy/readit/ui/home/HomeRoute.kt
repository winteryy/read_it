package com.winteryy.readit.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import com.winteryy.readit.model.SectionType
import com.winteryy.readit.ui.theme.ReadItTheme
import java.util.Date

@Composable
fun HomeRoute(
//    homeViewModel: HomeViewModel,
    homeUiState: HomeUiState,
    modifier: Modifier = Modifier,
) {
    //val homeUiState by homeViewModel...
    val sectionLazyListState = rememberLazyListState()

    Column(
        modifier = modifier
    ) {
        HomeTopBar(
            homeTopBarType = when(homeUiState) {
                is HomeUiState.FeedState -> HomeTopBarType.SearchBar.Default
                is HomeUiState.SearchState -> HomeTopBarType.SearchBar.Searching
                is HomeUiState.SearchResultState -> HomeTopBarType.SearchBar.Searching
            },
        )

        HorizontalDivider()

        when(homeUiState) {
            is HomeUiState.FeedState -> {
                HomeFeedScreen(
                    sectionList = homeUiState.sectionList,
                    sectionLazyListState = sectionLazyListState
                )
            }
            is HomeUiState.SearchState -> HomeSearchScreen()
            is HomeUiState.SearchResultState -> {
                HomeSearchResultScreen(
                    bookList = homeUiState.bookList
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeRoutePreview() {
    val dummySection = Section(
        sectionType = SectionType.READING,
        bookList = listOf(
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
                "13214130",
                description = "asdasd",
                pubDate = Date(),
            ),
            Book(
                "정의란 무엇인가",
                "https://picsum.photos/300/400",
                "test author",
                "test publisher",
                "13214129",
                description = "asdasd",
                pubDate = Date(),
            ),
            Book(
                "정의란 무엇인가",
                "https://picsum.photos/300/400",
                "test author",
                "test publisher",
                "13214128",
                description = "asdasd",
                pubDate = Date(),
            ),
            Book(
                "정의란 무엇인가",
                "https://picsum.photos/300/400",
                "test author",
                "test publisher",
                "13214127",
                description = "asdasd",
                pubDate = Date(),
            ),
            Book(
                "정의란 무엇인가",
                "https://picsum.photos/300/400",
                "test author",
                "test publisher",
                "13214126",
                description = "asdasd",
                pubDate = Date(),
            ),
            Book(
                "정의란 무엇인가",
                "https://picsum.photos/300/400",
                "test author",
                "test publisher",
                "13214125",
                description = "asdasd",
                pubDate = Date(),
            ),
            Book(
                "정의란 무엇인가",
                "https://picsum.photos/300/400",
                "test author",
                "test publisher",
                "13214124",
                description = "asdasd",
                pubDate = Date(),
            ),
            Book(
                "정의란 무엇인가",
                "https://picsum.photos/300/400",
                "test author",
                "test publisher",
                "13214121",
                description = "asdasd",
                pubDate = Date(),
            ),
            Book(
                "정의란 무엇인가",
                "https://picsum.photos/300/400",
                "test author",
                "test publisher",
                "13214122",
                description = "asdasd",
                pubDate = Date(),
            ),
            Book(
                "정의란 무엇인가",
                "https://picsum.photos/300/400",
                "test author",
                "test publisher",
                "13214123",
                description = "asdasd",
                pubDate = Date(),
            )
        )
    )
    ReadItTheme {
        HomeRoute(
            HomeUiState.FeedState(
                sectionList = listOf(
                    dummySection, dummySection, dummySection, dummySection
                )
            )
        )
    }
}
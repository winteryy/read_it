package com.winteryy.readit.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import com.winteryy.readit.model.SectionType
import com.winteryy.readit.ui.theme.ReadItTheme
import java.util.Date

enum class HomeScreenType {
    FEED, SEARCH, SEARCH_RESULT
}

@Composable
fun HomeRoute(
    sectionList: List<Section>,
    modifier: Modifier = Modifier
) {

    var homeScreenType by rememberSaveable { mutableStateOf(HomeScreenType.FEED) }
    var homeTopBarType by rememberSaveable { mutableStateOf(HomeTopBarType.SearchBar.Search) }

    Column(
        modifier = modifier
    ) {
        HomeTopBar(
            homeTopBarType = homeTopBarType,
            modifier = Modifier.padding(16.dp),
        )
        HorizontalDivider()

        when(homeScreenType) {
            HomeScreenType.FEED -> {
                HomeFeedScreen(
                    sectionList = sectionList,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            HomeScreenType.SEARCH -> TODO()
            HomeScreenType.SEARCH_RESULT -> TODO()
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
            listOf(
                dummySection, dummySection, dummySection, dummySection
            )
        )
    }
}
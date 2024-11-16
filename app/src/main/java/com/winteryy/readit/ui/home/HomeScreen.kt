package com.winteryy.readit.ui.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import com.winteryy.readit.model.SectionType
import com.winteryy.readit.ui.components.BookListColumn
import com.winteryy.readit.ui.components.SectionItem
import com.winteryy.readit.ui.theme.DEFAULT_PADDING
import com.winteryy.readit.ui.theme.ReadItTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeFeedScreen(
    sectionList: List<Section>,
    modifier: Modifier = Modifier,
    onSectionArrowClicked: (Section) -> Unit = {},
    onSectionItemClicked: (Book) -> Unit = {},
    sectionLazyListState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = sectionLazyListState,
        modifier = modifier
    ) {
        itemsIndexed(
            items = sectionList,
            key = { _, section -> section.sectionType.id }
        ) { ind, section ->
            Spacer(modifier = Modifier.size(16.dp))
            SectionItem(
                section = section,
                itemWidth = 100.dp,
                onArrowClicked = onSectionArrowClicked,
                onItemClicked = onSectionItemClicked
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun HomeSearchScreen(
    modifier: Modifier = Modifier
) {

}

@Composable
fun HomeSearchResultScreen(
    bookPagingDataFlow: Flow<PagingData<Book>>,
    modifier: Modifier = Modifier,
    onResultItemClicked: (Book) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    val searchLazyItems = bookPagingDataFlow.collectAsLazyPagingItems()

    BookListColumn(
        lazyPagingBooks = searchLazyItems,
        lazyListState = lazyListState,
        modifier = modifier
            .padding(horizontal = DEFAULT_PADDING),
        onItemClicked = onResultItemClicked
    )
}

@Composable
fun HomeSectionDetailScreen(
    bookPagingDataFlow: Flow<PagingData<Book>>,
    sectionType: SectionType,
    modifier: Modifier = Modifier,
    onBookItemClicked: (Book) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    val sectionBookLazyItems = bookPagingDataFlow.collectAsLazyPagingItems()

    BookListColumn(
        lazyPagingBooks = sectionBookLazyItems,
        lazyListState = lazyListState,
        modifier = modifier
            .padding(horizontal = DEFAULT_PADDING),
        showRating = sectionType.showRating,
        onItemClicked = onBookItemClicked
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeFeedScreen() {
    ReadItTheme {
        HomeFeedScreen(
            emptyList()
        )
    }
}
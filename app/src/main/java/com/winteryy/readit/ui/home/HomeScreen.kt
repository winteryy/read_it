package com.winteryy.readit.ui.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import com.winteryy.readit.ui.components.BookListColumn
import com.winteryy.readit.ui.components.SectionItem
import com.winteryy.readit.ui.theme.ReadItTheme

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
            .padding(horizontal = 16.dp)
    ) {
        itemsIndexed(
            items = sectionList,
            key = { _, section -> section.sectionType.id }
        ) { ind, section ->
            Spacer(modifier = Modifier.size(16.dp))
            SectionItem(
                section = section,
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
    query: String,
    bookList: List<Book>,
    modifier: Modifier = Modifier,
    onResultItemClicked: (Book) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(query) {
        lazyListState.scrollToItem(0)
    }

    BookListColumn(
        bookList = bookList,
        lazyListState = lazyListState,
        modifier = modifier
            .padding(horizontal = 16.dp),
        onItemClicked = onResultItemClicked
    )
}

@Composable
fun HomeSectionDetailScreen(
    bookList: List<Book>,
    modifier: Modifier = Modifier,
    onBookItemClicked: (Book) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()

    BookListColumn(
        bookList = bookList,
        lazyListState = lazyListState,
        modifier = modifier
            .padding(horizontal = 16.dp),
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
package com.winteryy.readit.ui.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.model.Book
import com.winteryy.readit.model.Section
import com.winteryy.readit.ui.components.SectionItem
import com.winteryy.readit.ui.theme.ReadItTheme

enum class HomeScreenType {
    FEED, SEARCH, SEARCH_RESULT
}

@Composable
fun HomeFeedScreen(
    sectionList: List<Section>,
    modifier: Modifier = Modifier,
    onSectionArrowClicked: (Section) -> Unit = {},
    onSectionItemClicked: (Book) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        itemsIndexed(
            items = sectionList,
            key = { ind, section -> ind } //todo section 고유 값으로 수정할 것
        ) { ind, section ->
            Spacer(modifier = Modifier.size(16.dp))
            SectionItem(
                section = section,
                onArrowClicked = onSectionArrowClicked,
                onItemClicked = onSectionItemClicked)
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun HomeSearchResultScreen(
    modifier: Modifier = Modifier
) {

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
package com.winteryy.readit.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.model.Section
import com.winteryy.readit.ui.theme.ReadItTheme

@Composable
fun SectionItem(
    section: Section,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = stringResource(id = section.title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .paddingFromBaseline(bottom = 16.dp)
        )
        LazyRow {
            itemsIndexed(
                items = section.bookList,
                key = { _, book -> book.isbn }
            ) { _, book ->
                BookItem(book = book)
            }
        }
    }
}

@Preview
@Composable
fun PreviewSectionItem() {
    ReadItTheme {
    }
}
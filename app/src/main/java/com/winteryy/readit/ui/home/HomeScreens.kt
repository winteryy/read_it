package com.winteryy.readit.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.winteryy.readit.data.Result
import com.winteryy.readit.model.Section
import com.winteryy.readit.ui.components.SectionItem
import com.winteryy.readit.ui.theme.ReadItTheme

@Composable
fun HomeFeedScreen(
    sectionList: List<Section>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(
            items = sectionList,
            key = { ind, section -> ind } //todo section 고유 값으로 수정할 것
        ) { ind, section ->
            Spacer(modifier = Modifier.size(16.dp))
            SectionItem(section = section)
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}



@Composable
fun HomeTestScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val testResult = viewModel.testBookFlow.collectAsState()

    Column {
        Text(text = "Home")
        Button(
            onClick = {
                //viewModel.repoTest()
            },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.DarkGray
            )
        ) {
            Text(text = "search test button")
        }
        Button(
            onClick = {
                viewModel.insertBookTest1()
            },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.DarkGray
            )
        ) {
            Text(text = "insert1")
        }
        Button(
            onClick = {
                viewModel.insertBookTest2()
            },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.DarkGray
            )
        ) {
            Text(text = "insert2")
        }
        Text(
            text = when (val result = testResult.value) {
                is Result.Error -> result.exception.message.toString()
                is Result.Success -> result.data.toString()
            }
        )

    }
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
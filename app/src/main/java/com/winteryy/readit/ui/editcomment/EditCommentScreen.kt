package com.winteryy.readit.ui.editcomment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.winteryy.readit.ui.components.TextTopBar
import com.winteryy.readit.ui.theme.DEFAULT_PADDING
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography
import com.winteryy.readit.ui.theme.theme_grey_whiteSmoke

@Composable
fun EditCommentScreen() {
    val testText = "내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 "
    //viewModel에 State 분리
    val scrollableState = rememberScrollState()
    val isEditing = remember { mutableStateOf(false) }

    Column(
    ) {
        TextTopBar(
            title = "정의란 무엇인가",
            backButtonImageVector = Icons.Filled.Close,
            trailingText = if(isEditing.value) "완료" else "삭제",
            trailingTextCallback = { isEditing.value = false }
        )

        HorizontalDivider()

        Surface(
            modifier = Modifier
                .background(theme_grey_whiteSmoke)
                .padding(DEFAULT_PADDING)
                .weight(1f)
        ) {
            if(isEditing.value) {
                var textFieldString by rememberSaveable { mutableStateOf(testText) }
                TextField(
                    value = textFieldString,
                    textStyle = Typography.bodyMedium,
                    onValueChange = { textFieldString = it },
                    modifier = Modifier.fillMaxHeight()
                )
            } else {
                Text(
                    text = testText,
                    overflow = TextOverflow.Clip,
                    style = Typography.bodyMedium,
                    modifier = Modifier
                        .background(theme_grey_whiteSmoke)
                        .verticalScroll(
                            state = scrollableState
                        )
                        .clickable { isEditing.value = true }
                )
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun EditCommentScreenPreview() {
    ReadItTheme {
        EditCommentScreen()
    }
}
package com.winteryy.readit.ui.editcomment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.winteryy.readit.ui.components.TextTopBar
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.Typography

@Composable
fun EditCommentScreen() {
    val testText = "내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 \n 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 내용 확인 텍스트 "
    //viewModel에 State 분리
    val scrollableState = rememberScrollState()
    val isEditing = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    if(isEditing.value.not()) {
        focusManager.clearFocus()
    }

    Column(
    ) {
        TextTopBar(
            title = "정의란 무엇인가",
            backButtonImageVector = Icons.Filled.Close,
            trailingText = if(isEditing.value) "완료" else "삭제",
            trailingTextCallback = { isEditing.value = false }
        )

        HorizontalDivider()

        var textFieldString by rememberSaveable { mutableStateOf(testText) }
        TextField(
            value = textFieldString,
            textStyle = Typography.bodyMedium,
            onValueChange = { textFieldString = it },
            colors = TextFieldDefaults.colors().copy(
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxHeight()
                .onFocusChanged { focusState ->
                    if(focusState.isFocused) {
                        isEditing.value = true
                    }
                },
        )
    }

}

@Preview(showBackground = true)
@Composable
fun EditCommentScreenPreview() {
    ReadItTheme {
        EditCommentScreen()
    }
}
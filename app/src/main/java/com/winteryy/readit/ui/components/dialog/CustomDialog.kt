package com.winteryy.readit.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.theme_grey_black
import com.winteryy.readit.ui.theme.theme_grey_white

@Composable
fun CustomDialog(
    title: String,
    description: String,
    buttons: List<DialogButtonInfo>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = theme_grey_black.copy(alpha = 0.5f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = theme_grey_white,
                    shape = RoundedCornerShape(8.dp)
                )
                .size(300.dp, 200.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(title.isNotBlank()) {
                Text(
                    text = title,
                    style = typography.headlineSmall,
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = description,
                    style = typography.bodyMedium
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
            ) {
                repeat(buttons.size) {
                    CustomDialogButton(
                        dialogButtonInfo = buttons[it],
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CustomDialogPreview() {
    ReadItTheme {
        CustomDialog(
            "타이틀",
            "책 로드 실패.",
            listOf(
                DialogButtonInfo(
                    text = "확인",
                    type = DialogButtonType.FILLED,
                    onClick = {},
                ),
                DialogButtonInfo(
                    text = "취소",
                    type = DialogButtonType.OUTLINED,
                    onClick = {},
                )
            )
        )
    }
}
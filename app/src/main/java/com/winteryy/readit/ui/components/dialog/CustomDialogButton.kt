package com.winteryy.readit.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.winteryy.readit.ui.theme.ReadItTheme
import com.winteryy.readit.ui.theme.theme_color_lightDodgerBlue
import com.winteryy.readit.ui.theme.theme_grey_white

@Composable
fun CustomDialogButton(
    dialogButtonInfo: DialogButtonInfo,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = if(dialogButtonInfo.type == DialogButtonType.FILLED) theme_color_lightDodgerBlue
                    else theme_grey_white,
                shape = RoundedCornerShape(8.dp)
            )
            .border(2.dp, theme_color_lightDodgerBlue, RoundedCornerShape(8.dp))
            .height(40.dp)
            .clickable {
                dialogButtonInfo.onClick.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dialogButtonInfo.text,
            style = typography.labelMedium,
            color = if(dialogButtonInfo.type == DialogButtonType.FILLED) theme_grey_white
                else theme_color_lightDodgerBlue,
        )
    }
}

data class DialogButtonInfo(
    val text: String,
    val type: DialogButtonType,
    val onClick: () -> Unit
)

enum class DialogButtonType {
    FILLED, OUTLINED
}

@Composable
@Preview(showBackground = true)
fun CustomFilledDialogButtonPreview() {
    ReadItTheme {
        CustomDialogButton(
            dialogButtonInfo = DialogButtonInfo(
                text = "확인",
                type = DialogButtonType.FILLED,
                onClick = {}
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CustomOutlinedDialogButtonPreview() {
    ReadItTheme {
        CustomDialogButton(
            dialogButtonInfo = DialogButtonInfo(
                text = "확인",
                type = DialogButtonType.OUTLINED,
                onClick = {}
            )
        )
    }
}

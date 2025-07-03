package com.core.common.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.common.theme.LightGray2
import com.core.common.theme.TaskerTheme

@Composable
fun RoundedTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        colors =
            TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            ),
        modifier =
            modifier
                .border(
                    width = 1.dp,
                    color = LightGray2,
                    shape = RoundedCornerShape(12.dp),
                ).padding(horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
    )
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun RoundedTextFieldPreview() {
    TaskerTheme {
        Box(
            modifier = Modifier.padding(16.dp),
        ) {
            RoundedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = "Work",
                onTextChange = {},
            )
        }
    }
}

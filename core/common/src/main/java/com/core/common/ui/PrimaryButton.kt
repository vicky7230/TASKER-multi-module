package com.core.common.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.TaskerTheme

@Composable
fun PrimaryButton(
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonTextColor: Color = Color.Black,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
) {
    Button(
        modifier = modifier,
        onClick = onButtonClick,
        shape = shape,
        colors = buttonColors,
    ) {
        ButtonText(
            modifier = Modifier.padding(vertical = 8.dp),
            text = buttonText,
            color = buttonTextColor,
        )
    }
}

@Composable
fun ButtonText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
) {
    Text(
        modifier = modifier.padding(vertical = 4.dp),
        text = text,
        style =
            TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = color,
            ),
    )
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    TaskerTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = "Save",
                onButtonClick = {},
            )
        }
    }
}

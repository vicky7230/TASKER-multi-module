package com.core.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.common.theme.TaskerTheme

@Composable
fun ActionIcon(
    onClick: () -> Unit,
    backgroundColor: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Color.White,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.background(backgroundColor).padding(horizontal = 15.dp),
    ) {
        Icon(
            modifier =
                Modifier
                    .size(48.dp)
                    .padding(5.dp),
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
        )
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun PreviewActionIcon() {
    TaskerTheme {
        Box(
            modifier = Modifier.height(100.dp),
        ) {
            ActionIcon(
                onClick = { },
                backgroundColor = Color.Red,
                icon = ImageVector.vectorResource(id = com.core.common.R.drawable.ic_delete),
                modifier = Modifier.fillMaxHeight(),
                contentDescription = "Delete Icon",
            )
        }
    }
}

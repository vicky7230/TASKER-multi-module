@file:Suppress("MagicNumber")

package com.feature.notes.ui.screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.common.R
import com.core.common.theme.Blue
import com.core.common.theme.TaskerTheme

@Composable
fun FabMenu(
    options: List<FabOption>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        options.forEachIndexed { index, option ->
            FabMenuItem(
                modifier = Modifier.fillMaxWidth(0.65f),
                label = option.label,
                icon = option.icon,
                color = option.color,
                onClick = option.onClick,
            )
            if (index < options.size - 1) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth(0.65f))
            }
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FabMenuPreview() {
    TaskerTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            FabMenu(
                modifier =
                    Modifier
                        .padding(bottom = 90.dp, end = 16.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(8.dp),
                            clip = false,
                        ).background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .align(Alignment.BottomEnd),
                options =
                    listOf(
                        FabOption(
                            label = "Task",
                            icon = ImageVector.vectorResource(R.drawable.ic_create_task),
                            color = Blue,
                            onClick = {},
                        ),
                        FabOption(
                            label = "List",
                            icon = ImageVector.vectorResource(R.drawable.ic_create_list),
                            color = Blue,
                            onClick = {},
                        ),
                    ),
            )
        }
    }
}

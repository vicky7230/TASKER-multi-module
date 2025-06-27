@file:Suppress("MagicNumber")

package com.feature.add_edit_note.ui.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.TaskerTheme
import com.core.common.ui.Picker
import com.core.common.ui.PickerState
import com.core.common.ui.rememberPickerState

@Composable
fun VerticalTimePicker(
    expanded: Boolean,
    hoursPickerState: PickerState,
    minutesPickerState: PickerState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .animateContentSize()
                .then(
                    if (expanded) {
                        Modifier.wrapContentHeight()
                    } else {
                        Modifier.height(0.dp)
                    },
                ),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            val hours =
                remember { (0..23).map { if (it.toString().length == 1) "0$it" else it.toString() } }

            Picker(
                state = hoursPickerState,
                items = hours,
                visibleItemsCount = 5,
                modifier = Modifier.weight(1f),
                textModifier = Modifier.padding(8.dp),
                textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Medium),
                dividerColor = Color(0xFFE8E8E8),
                wheelAlignment = Alignment.End,
            )

            val minutes =
                remember { (0..59).map { if (it.toString().length == 1) "0$it" else it.toString() } }

            Picker(
                state = minutesPickerState,
                items = minutes,
                visibleItemsCount = 5,
                modifier = Modifier.weight(1f),
                textModifier = Modifier.padding(8.dp),
                textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Medium),
                dividerColor = Color(0xFFE8E8E8),
                wheelAlignment = Alignment.Start,
            )
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun VerticalTimePickerPreview() {
    TaskerTheme {
        val hoursPickerState = rememberPickerState("02")
        val minutesPickerState = rememberPickerState("48")
        VerticalTimePicker(
            expanded = true,
            hoursPickerState = hoursPickerState,
            minutesPickerState = minutesPickerState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

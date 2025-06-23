package com.core.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.R
import com.core.common.theme.LightGray
import com.core.common.utils.toColorSafely
import com.core.domain.model.TagWithNotes

@Composable
fun TagItem(
    tag: TagWithNotes,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier.background(
                color = tag.color.toColorSafely(),
                shape = RoundedCornerShape(10.dp),
            ),
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 12.dp),
            text = tag.name,
            color = Color.White,
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, bottom = 12.dp, top = 4.dp),
            text = stringResource(R.string.task_count, tag.notes.size),
            color = LightGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

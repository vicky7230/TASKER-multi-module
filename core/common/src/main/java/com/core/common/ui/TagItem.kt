package com.core.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.core.common.R
import com.core.common.theme.LightGray
import com.core.common.theme.TaskerTheme
import com.core.common.utils.toColorSafely
import com.core.domain.model.Note
import com.core.domain.model.TagWithNotes

@Composable
fun TagItem(
    tag: TagWithNotes,
    onTagClick: (TagWithNotes) -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    ConstraintLayout(
        modifier =
            modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                    color = tag.color.toColorSafely(),
                    shape = RoundedCornerShape(10.dp),
                ).clickable {
                    onTagClick(tag)
                },
    ) {
        val (title, subtitle, checkedIcon) = createRefs()
        Text(
            modifier =
                Modifier.constrainAs(title) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top, margin = 12.dp)
                    bottom.linkTo(subtitle.top)
                },
            text = tag.name,
            color = Color.White,
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            modifier =
                Modifier.constrainAs(subtitle) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(title.bottom, margin = 4.dp)
                    bottom.linkTo(parent.bottom, margin = 12.dp)
                },
            text = stringResource(R.string.task_count, tag.notes.size),
            color = LightGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
        )

        if (selected) {
            Icon(
                modifier =
                    Modifier
                        .constrainAs(checkedIcon) {
                            end.linkTo(parent.end, margin = 20.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }.size(28.dp),
                painter = painterResource(com.core.common.R.drawable.ic_checked),
                contentDescription = "checkedIcon",
                tint = Color.Unspecified,
            )
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun PreviewTagItem() {
    TaskerTheme {
        Box(modifier = Modifier.background(Color.White)) {
            TagItem(
                tag =
                    TagWithNotes(
                        id = 1,
                        name = "Work",
                        color = "#61DEA4",
                        notes =
                            listOf(
                                Note(1, "Note 1", 1L, tagId = 1, done = false),
                                Note(2, "Note 2", 1L, tagId = 1, done = false),
                            ),
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp,
                        ),
                selected = true,
                onTagClick = {},
            )
        }
    }
}

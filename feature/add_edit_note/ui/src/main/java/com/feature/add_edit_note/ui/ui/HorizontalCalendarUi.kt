@file:SuppressLint("NewApi")
@file:Suppress("MagicNumber", "UnusedPrivateMember")

package com.feature.add_edit_note.ui.ui

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.common.theme.Blue
import com.core.common.theme.LightGray
import com.core.common.theme.LightGray4
import com.core.common.theme.TaskerTheme
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalCalendarUi(
    expanded: Boolean,
    onDateSelect: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val currentMonth = remember { YearMonth.now() }
        val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
        val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
        val daysOfWeek = remember { daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY) }
        var selectedDate: LocalDate? by remember { mutableStateOf(LocalDate.now()) }

        val state =
            rememberCalendarState(
                startMonth = startMonth,
                endMonth = endMonth,
                firstVisibleMonth = currentMonth,
                firstDayOfWeek = daysOfWeek.first(),
            )

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                Day(
                    day = day,
                    isSelected = selectedDate == day.date,
                    onClick = { day ->
                        selectedDate = day.date
                        onDateSelect(day.date)
                    },
                    modifier = Modifier,
                )
            },
            monthHeader = { calendarMonth: CalendarMonth ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    MonthYearTitle(
                        modifier = Modifier.padding(bottom = 30.dp),
                        calendarMonth = calendarMonth,
                    )
                    DaysOfWeekTitle(
                        daysOfWeek = daysOfWeek,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            },
        )
    }
}

@Composable
fun DaysOfWeekTitle(
    daysOfWeek: List<DayOfWeek>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = LightGray4,
                fontWeight = FontWeight.SemiBold,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).uppercase(),
            )
        }
    }
}

@Composable
fun MonthYearTitle(
    calendarMonth: CalendarMonth,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Text(
            text =
                calendarMonth.yearMonth.month.getDisplayName(
                    TextStyle.SHORT,
                    Locale.getDefault(),
                ),
            style =
                androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                ),
        )
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = calendarMonth.yearMonth.year.toString(),
            style =
                androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DaysOfWeekTitlePreview() {
    TaskerTheme {
        DaysOfWeekTitle(
            modifier = Modifier.fillMaxWidth(),
            daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY),
        )
    }
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(48.dp)
                .clickable(
                    enabled = day.position == DayPosition.MonthDate && day.date >= LocalDate.now(),
                    onClick = { onClick(day) },
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color =
                if (isSelected) {
                    Blue
                } else if (day.position == DayPosition.MonthDate) {
                    Color.Black
                } else {
                    LightGray
                },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DayPreview() {
    TaskerTheme {
        Day(
            day = CalendarDay(LocalDate.now(), DayPosition.MonthDate),
            isSelected = false,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHorizontalCalendarUi() {
    TaskerTheme {
        HorizontalCalendarUi(
            expanded = true,
            onDateSelect = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

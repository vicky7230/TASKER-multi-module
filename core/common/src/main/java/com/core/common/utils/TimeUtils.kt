package com.core.common.utils

import java.util.Calendar

object TimeUtils {
    fun isTimestampToday(timestampMillis: Long): Boolean {
        val timestampCalendar =
            Calendar.getInstance().apply {
                timeInMillis = timestampMillis
                // Zero out time to compare only the date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

        val todayCalendar =
            Calendar.getInstance().apply {
                // Zero out time to compare only the date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

        return timestampCalendar.timeInMillis == todayCalendar.timeInMillis
    }
}

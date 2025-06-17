package com.core.common.utils

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(value = Parameterized::class)
class TimeUtilsParameterizedTest(
    val inputTimeStamp: Long,
    val expectedResult: Boolean,
) {
    @Test
    fun testIsTimeValid() {
        assertEquals(expectedResult, TimeUtils.isTimestampToday(inputTimeStamp))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: isTimestampToday({0})={1}")
        fun data(): List<Array<Any>> =
            listOf(
                arrayOf(System.currentTimeMillis(), true),
                arrayOf(System.currentTimeMillis(), true),
                arrayOf(1633024800000, false),
            )
    }
}

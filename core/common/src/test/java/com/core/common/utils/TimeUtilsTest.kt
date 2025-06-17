package com.core.common.utils

import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class TimeUtilsTest {
    @Before
    fun setUp() {
        println("Before test")
    }

    @After
    fun tearDown() {
        println("After test")
    }

    @Test
    fun isTimestampToday_inputIsCurrentTimestamp_returnsTrue() {
        // Arrange
        val currentTimestamp = System.currentTimeMillis()
        // Act
        val result = TimeUtils.isTimestampToday(currentTimestamp)
        // Assert
        assertEquals(result, true)
    }

    @Test
    fun isTimestampToday_inputIsNotCurrentTimestamp_returnsFalse() {
        // Arrange
        val notCurrentTimestamp = 1633024800000L // 1 October 2021
        // Act
        val result = TimeUtils.isTimestampToday(notCurrentTimestamp)
        // Assert
        assertEquals(result, false)
    }
}

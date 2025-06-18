package com.core.network

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class ApiServiceTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        apiService =
            Retrofit
                .Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
                .create(ApiService::class.java)
    }

    @Test
    fun `getProducts should return products`() =
        runTest {
            // Arrange
            val mockResponse = MockResponse()
            val content = Helper.readFileResource("/response.json")
            mockResponse.setResponseCode(200)
            mockResponse.setBody(content)
            mockWebServer.enqueue(mockResponse)

            // Act
            val response = apiService.getProducts()
            mockWebServer.takeRequest()

            // Assert
            assertEquals(2, response.size)
        }

    @Test
    fun `getProducts should return error`() =
        runTest {
            // Arrange
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(500),
            )

            // Act
            try {
                apiService.getProducts()
            } catch (exception: HttpException) {
                // Assert
                assertEquals(500, exception.code())
            }
        }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}

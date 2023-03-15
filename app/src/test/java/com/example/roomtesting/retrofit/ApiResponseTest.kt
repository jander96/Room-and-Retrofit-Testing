package com.example.roomtesting.retrofit


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class ApiResponseTest {
    private val baseMockUrl = "/"
    private val allUserPath = "todos/"
    private val userId  = 1
    private val userPath = "$userId"
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiServices: ApiServices

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        apiServices = Retrofit.Builder()
            .baseUrl(mockWebServer.url(baseMockUrl))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiServices::class.java)
    }


    @Test
    fun `the list from api is not empty`() = runTest {
        // Given
        mockWebServer.dispatcher = getDispatcherForAllUser

        // When
        val listUser = apiServices.getAllUser()
        assert(listUser.isNotEmpty())
    }
    @Test
    fun `request user by id returns the correct user`() = runTest {
        // Given
        mockWebServer.dispatcher = getDispatcherForAllUser

        // When
        val user = apiServices.getUserById(userId)
        // Then
       assert(user.title == "delectus aut autem")
    }

    private val getDispatcherForAllUser = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
               baseMockUrl+allUserPath -> MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        "[\n" +
                                "  {\n" +
                                "    \"userId\": 1,\n" +
                                "    \"id\": 1,\n" +
                                "    \"title\": \"delectus aut autem\",\n" +
                                "    \"completed\": false\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"userId\": 1,\n" +
                                "    \"id\": 2,\n" +
                                "    \"title\": \"quis ut nam facilis et officia qui\",\n" +
                                "    \"completed\": false\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"userId\": 1,\n" +
                                "    \"id\": 3,\n" +
                                "    \"title\": \"fugiat veniam minus\",\n" +
                                "    \"completed\": false\n" +
                                "  }" +
                                "]"
                    )
                baseMockUrl+allUserPath + userPath -> MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        "{\n" +
                                "  \"userId\": 1,\n" +
                                "  \"id\": 1,\n" +
                                "  \"title\": \"delectus aut autem\",\n" +
                                "  \"completed\": false\n" +
                                "}"
                    )
                else -> MockResponse().setResponseCode(403)
            }
        }
    }


    @After
    fun cleanup() {
        mockWebServer.shutdown()
    }
}
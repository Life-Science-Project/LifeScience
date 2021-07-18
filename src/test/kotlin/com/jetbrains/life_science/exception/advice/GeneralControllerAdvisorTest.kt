package com.jetbrains.life_science.exception.advice

import com.jetbrains.life_science.ApiTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GeneralControllerAdvisorTest : ApiTest() {

    /**
     * Test checks exception handling on wrong json format
     *
     * Expected 400 http code and 400_999 systemCode response
     */
    @Test
    fun `handle bad json dto`() {
        val badJson = "{ awdwd\" }"
        val request = postRequest("/api/search", badJson)

        val response = getApiExceptionView(400, request)
        assertEquals(400_999, response.systemCode)
        assertTrue(response.arguments.isEmpty())
    }
}

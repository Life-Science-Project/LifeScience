package com.jetbrains.life_science.util.validators

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class UniqueListElementsTest {
    private val validator = UniqueCollectionElementsValidator()

    @Test
    fun `should find no validation error, if value is null`() {
        assertTrue(validator.isValid(value = null, context = null))
    }

    @Test
    fun `should find no validation error, if value represented by set`() {
        val set = setOf(
            ListElement(1, "alpha"),
            ListElement(1, "alpha"),
            ListElement(1, "alpha"),
            ListElement(1, "alpha")
        )

        assertTrue(validator.isValid(value = set, context = null))
    }

    @Test
    fun `should find no validation error, if value contains only unique elements`() {
        val list = listOf(
            ListElement(1, "alpha"),
            ListElement(2, "beta"),
            ListElement(3, "gamma"),
            ListElement(4, "delta"),
            ListElement(5, "epsilon")
        )

        assertTrue(validator.isValid(value = list, context = null))
    }

    @Test
    fun `should return validation error, if value contains not unique elements`() {
        val list = listOf(
            ListElement(1, "alpha"),
            ListElement(2, "beta"),
            ListElement(3, "gamma"),
            ListElement(1, "alpha"),
            ListElement(4, "delta")
        )

        assertFalse(validator.isValid(value = list, context = null))
    }

    @Test
    fun `should find no validation error, if value contains no elements`() {
        assertTrue(validator.isValid(value = emptyList<ListElement>(), context = null))
    }
}

data class ListElement(
    val id: Long,
    val name: String
)

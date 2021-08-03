package com.jetbrains.life_science.container.protocol.parameter.service

import com.jetbrains.life_science.container.protocol.parameter.entity.ProtocolParameter
import com.jetbrains.life_science.container.protocol.parameter.service.maker.makeProtocolParameterInfo
import com.jetbrains.life_science.exception.not_found.ProtocolParameterNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/initial_data.sql", "/scripts/protocol/parameter/parameters_data.sql")
@Transactional
class ProtocolParameterServiceTest {

    @Autowired
    lateinit var service: ProtocolParameterService

    @Test
    fun `get existing parameter`() {
        // Prepare
        val parameterId = 1L
        val expected = ProtocolParameter(
            id = parameterId,
            name = "Mass",
            value = "12 * x + 3"
        )

        // Action
        val parameter = service.get(parameterId)

        // Assert
        assertEquals(expected.id, parameter.id)
        assertEquals(expected.name, parameter.name)
        assertEquals(expected.value, parameter.value)
    }

    @Test
    fun `get not existing parameter`() {
        // Prepare
        val parameterId = 666L

        // Action & Assert
        assertThrows<ProtocolParameterNotFoundException> {
            service.get(parameterId)
        }
    }

    @Test
    fun `create parameter`() {
        // Prepare
        val info = makeProtocolParameterInfo(
            id = 0,
            name = "Colour",
            value = "red"
        )

        // Action
        val createdParam = service.create(info)
        val param = service.get(createdParam.id)

        // Assert
        assertEquals(createdParam.id, param.id)
        assertEquals(info.name, param.name)
        assertEquals(info.value, param.value)
    }

    @Test
    fun `update existing parameter`() {
        // Prepare
        val info = makeProtocolParameterInfo(
            id = 2,
            name = "Colour",
            value = "red"
        )

        // Action
        service.update(info)
        val param = service.get(info.id)

        // Assert
        assertEquals(info.id, param.id)
        assertEquals(info.name, param.name)
        assertEquals(info.value, param.value)
    }

    @Test
    fun `update not existing parameter`() {
        // Prepare
        val info = makeProtocolParameterInfo(
            id = 666,
            name = "Colour",
            value = "red"
        )

        // Action & Assert
        assertThrows<ProtocolParameterNotFoundException> {
            service.update(info)
        }
    }

    @Test
    fun `delete existing parameter`() {
        // Prepare
        val parameterId = 3L

        // Action
        service.delete(parameterId)

        // Assert
        assertThrows<ProtocolParameterNotFoundException> {
            service.get(parameterId)
        }
    }

    @Test
    fun `delete not existing parameter`() {
        // Prepare
        val parameterId = 666L

        // Action & Assert
        assertThrows<ProtocolParameterNotFoundException> {
            service.delete(parameterId)
        }
    }
}

package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.article.repository.ArticleRepository
import com.jetbrains.life_science.container.entity.ContainerInfo
import com.jetbrains.life_science.container.repository.ContainerRepository
import com.jetbrains.life_science.exceptions.MethodNotFoundException
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.repository.MethodRepository
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.repository.SectionRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.junit.jupiter.api.assertThrows
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class ContainerServiceImplTest {

    @MockBean
    lateinit var articleRepository: ArticleRepository

    @Autowired
    lateinit var containerService: ContainerService

    @Autowired
    lateinit var methodRepository: MethodRepository

    @Autowired
    lateinit var sectionRepository: SectionRepository

    @Autowired
    lateinit var containerRepository: ContainerRepository

    @BeforeEach
    @Sql("/scripts/test_trunc_data.sql")
    internal fun setUp() {

    }

    @Test
    @Transactional
    @Sql("/scripts/test_common_data.sql")
    internal fun `container creation test`() {
        val info = mock<ContainerInfo> {
            on { id } doReturn 0
            on { description } doReturn "test"
            on { methodId } doReturn 1
            on { name } doReturn "test name"
        }

        val createdContainer = containerService.create(info)

        val container = containerRepository.findById(createdContainer.id).orElseThrow()

        assertEquals(1L, container.method.id)
        assertEquals("test name", container.name)
        assertEquals("test", container.description)
    }

    @Test
    @Transactional
    @Sql("/scripts/test_common_data.sql")
    internal fun `container creation test wrong method id`() {
        val info = mock<ContainerInfo> {
            on { id } doReturn (0)
            on { description } doReturn ("test")
            on { methodId } doReturn (-1)
            on { name } doReturn ("test name")
        }

        assertThrows<MethodNotFoundException> { containerService.create(info) }
    }

    @Test
    @Transactional
    @Sql("/scripts/test_common_data.sql")
    internal fun `container not exists by id test`() {
        val result = containerService.existsById(-1L)
        assertFalse(result)
    }

    @Test
    @Transactional
    @Sql("/scripts/test_common_data.sql")
    internal fun `container exists by id test`() {
        val info = mock<ContainerInfo> {
            on { id } doReturn (0)
            on { description } doReturn ("test")
            on { methodId } doReturn (1L)
            on { name } doReturn ("test name")
        }
        val container = containerService.create(info)

        assertTrue(containerService.existsById(container.id))
    }
}

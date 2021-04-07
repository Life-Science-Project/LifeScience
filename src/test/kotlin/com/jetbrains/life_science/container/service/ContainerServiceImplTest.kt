package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.article.repository.ArticleRepository
import com.jetbrains.life_science.container.repository.ContainerRepository
import com.jetbrains.life_science.exceptions.ContainerNotFoundException
import com.jetbrains.life_science.exceptions.GeneralInformationDeletionException
import com.jetbrains.life_science.exceptions.MethodNotFoundException
import com.jetbrains.life_science.method.repository.MethodRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class ContainerServiceImplTest {

    @MockBean
    lateinit var articleRepository: ArticleRepository

    @Autowired
    lateinit var containerService: ContainerService

    @Autowired
    lateinit var containerRepository: ContainerRepository

    @Autowired
    lateinit var methodRepository: MethodRepository

    @BeforeEach
    @Sql("/scripts/test_trunc_data.sql")
    internal fun setUp() {
        reset(articleRepository)
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

        val container = containerRepository.getOne(createdContainer.id)

        assertEquals(1L, container.method.id)
        assertEquals("test name", container.name)
        assertEquals("test", container.description)
    }

    @Test
    @Transactional
    @Sql("/scripts/test_common_data.sql")
    internal fun `container creation test wrong method id`() {
        val info = mock<ContainerInfo> {
            on { id } doReturn 0
            on { description } doReturn "test"
            on { methodId } doReturn -1
            on { name } doReturn "test name"
        }

        assertThrows<MethodNotFoundException> { containerService.create(info) }
    }

    @Test
    @Transactional
    @Sql("/scripts/test_common_data.sql")
    internal fun `container not exists by id test`() {
        assertThrows<ContainerNotFoundException> { containerService.checkExistsById(-1) }
    }

    @Test
    @Transactional
    @Sql("/scripts/test_common_data.sql")
    internal fun `container exists by id test`() {
        val info = mock<ContainerInfo> {
            on { id } doReturn 0
            on { description } doReturn "test"
            on { methodId } doReturn 1L
            on { name } doReturn "test name"
        }
        val container = containerService.create(info)

        containerService.checkExistsById(container.id)
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `container delete by id test`() {
        assertTrue(containerRepository.existsById(2L))

        containerService.deleteById(2)

        assertFalse(containerRepository.existsById(2L))
        verify(articleRepository, times(1)).deleteAllByContainerId(2)
        // Check that parent method was not deleted
        assertTrue(methodRepository.existsById(1))
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `general information container delete attempt test`() {
        assertThrows<GeneralInformationDeletionException> { containerService.deleteById(1) }
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `container delete by wrong id test`() {
        assertThrows<ContainerNotFoundException> { containerService.deleteById(-1) }
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `container prepare deletion by wrong id test`() {
        assertThrows<ContainerNotFoundException> { containerService.deleteById(-1) }
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `container update with wrong id test`() {

        val info = mock<ContainerUpdateInfo> {
            on { id } doReturn -1
            on { description } doReturn "test"
            on { name } doReturn "test name"
        }

        assertThrows<ContainerNotFoundException> { containerService.update(info) }
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `container update test`() {

        val info = mock<ContainerUpdateInfo> {
            on { id } doReturn 2
            on { description } doReturn "updated description"
            on { name } doReturn "updated name"
        }

        containerService.update(info)

        val container = containerRepository.getOne(2)
        assertEquals("updated name", container.name)
        assertEquals("updated description", container.description)
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `container prepare deletion test`() {
        containerService.clearArticles(2)

        verify(articleRepository, times(1)).deleteAllByContainerId(2)
        // Check that parent method was not deleted
        assertTrue(methodRepository.existsById(1))
    }
}

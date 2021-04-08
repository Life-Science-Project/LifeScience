package com.jetbrains.life_science.article.service

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.repository.ArticleRepository
import com.jetbrains.life_science.exceptions.ArticleNotFoundException
import com.jetbrains.life_science.exceptions.ContainerNotFoundException
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class ArticleServiceImplTest {

    @Autowired
    lateinit var articleService: ArticleService

    @MockBean
    lateinit var articleRepository: ArticleRepository

    @BeforeEach
    @Sql("/scripts/test_trunc_data.sql")
    internal fun setUp() {
        reset(articleRepository)
    }

    @Test
    @Transactional
    @Sql("/scripts/test_common_data.sql")
    internal fun `article creation test`() {
        val articleInfo = mock<ArticleInfo> {
            on { text } doReturn "sample text"
            on { id } doReturn null
            on { containerId } doReturn 1L
            on { references } doReturn mutableListOf("r1", "r2")
            on { tags } doReturn mutableListOf("tag 1", "tag 2")
        }

        articleService.create(articleInfo)

        val argument = ArgumentCaptor.forClass(Article::class.java)

        verify(articleRepository, times(1)).save(argument.capture())
        assertEquals(1L, argument.value.containerId)
        assertEquals("sample text", argument.value.text)
        assertEquals(mutableListOf("r1", "r2"), argument.value.references)
        assertEquals(mutableListOf("tag 1", "tag 2"), argument.value.tags)
    }

    @Test
    @Transactional
    @Sql("/scripts/test_common_data.sql")
    internal fun `article creation test with wrong container id`() {

        val articleInfo = mock<ArticleInfo> {
            on { text } doReturn "sample text"
            on { id } doReturn null
            on { containerId } doReturn -1L
            on { references } doReturn mutableListOf("ref1", "ref2")
            on { tags } doReturn mutableListOf("tag 1", "tag 2")
        }

        assertThrows<ContainerNotFoundException> { articleService.create(articleInfo) }
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `article delete test`() {
        Mockito.`when`(articleRepository.existsById("123")).thenReturn(true)

        articleService.delete("123")

        verify(articleRepository, times(1)).deleteById("123")
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `article delete wrong id test`() {
        assertThrows<ArticleNotFoundException> { articleService.delete("123") }
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `article update text test`() {
        Mockito.`when`(articleRepository.existsById("123")).thenReturn(true)

        articleService.updateText("123", "text")

        verify(articleRepository, times(1)).updateText("123", "text")
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `article update text wrong id test`() {
        assertThrows<ArticleNotFoundException> { articleService.updateText("123", "text") }
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `find by container id test`() {
        val a1 = Article(1, "text 1", mutableListOf(), mutableListOf(), id = "1")
        val a2 = Article(1, "text 2", mutableListOf(), mutableListOf(), id = "2")
        val a3 = Article(1, "text 3", mutableListOf(), mutableListOf(), id = "3")

        Mockito.`when`(articleRepository.findAllByContainerId(1)).thenReturn(listOf(a1, a2, a3))

        val result = articleService.findAllByContainerId(1L)

        assertTrue(result.size == 3)
        assertEquals("1", result[0].id!!)
        assertEquals("2", result[1].id!!)
        assertEquals("3", result[2].id!!)
        assertEquals("text 1", result[0].text)
        assertEquals("text 1", result[0].text)
        assertEquals("text 1", result[0].text)
    }
}

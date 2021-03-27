package com.jetbrains.life_science

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.entity.ArticleInfo
import com.jetbrains.life_science.article.service.ArticleServiceImpl
import com.jetbrains.life_science.exceptions.MethodNotFoundException
import com.jetbrains.life_science.method.dto.MethodDTO
import com.jetbrains.life_science.method.dto.MethodDTOToInfoAdapter
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.repository.MethodRepository
import com.jetbrains.life_science.method.service.MethodService
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class MethodServiceTests @Autowired constructor(val methodService: MethodService) {

    private fun <T> any(type: Class<T>): T = Mockito.any(type)

    @MockBean
    lateinit var methodRepository: MethodRepository

    @MockBean
    lateinit var articleService: ArticleServiceImpl

    @MockBean
    lateinit var sectionService: SectionServiceImpl

    @Test
    fun methodAdd() {
        //Given
        Mockito
            .`when`(articleService.addArticle(any(ArticleInfo::class.java)))
            .thenReturn(Article(666))

        Mockito
            .`when`(sectionService.getSection(anyLong()))
            .thenReturn(Section(666, "Test session"))

        //When
        val methodInfo = MethodDTOToInfoAdapter(MethodDTO("Method #21321", 1))
        methodService.addMethod(methodInfo)

        //Then
        //No exceptions =)
    }

    @Test
    fun methodGet() {
        //Given
        val section = Section(16, "Section you got")
        val article = Article(45)
        val method = Method(12, "Method you got", section, article)

        Mockito
            .`when`(methodRepository.existsById(666))
            .thenReturn(false)

        Mockito
            .`when`(methodRepository.existsById(12))
            .thenReturn(true)

        Mockito
            .`when`(methodRepository.getOne(12))
            .thenReturn(method)

        //Then
        assertThatThrownBy { methodService.getMethod(666) }.isInstanceOf(MethodNotFoundException::class.java)
        assertThat(methodService.getMethod(12)).isEqualTo(method)

    }

    @Test
    fun methodDelete() {

        //Given
        val section = Section(1, "first section")
        val article = Article(1)
        val method = Method(1, "First method", section, article)

        Mockito
            .`when`(methodRepository.existsById(666))
            .thenReturn(false)

        Mockito
            .`when`(methodRepository.existsById(1))
            .thenReturn(true)

        Mockito
            .`when`(methodRepository.getOne(1))
            .thenReturn(method)

        //Then
        assertThatThrownBy { methodService.deleteMethod(666) }.isInstanceOf(MethodNotFoundException::class.java)
        methodService.deleteMethod(1)
    }
}
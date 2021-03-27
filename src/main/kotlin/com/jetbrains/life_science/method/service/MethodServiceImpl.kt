package com.jetbrains.life_science.method.service

import com.jetbrains.life_science.article.service.ArticleServiceImpl
import com.jetbrains.life_science.exceptions.MethodNotFoundException
import com.jetbrains.life_science.method.repository.MethodRepository
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.entity.MethodInfo
import com.jetbrains.life_science.section.service.SectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MethodServiceImpl @Autowired constructor(
    val methodRepository: MethodRepository,
    val sectionService: SectionService,
    val articleService: ArticleServiceImpl
) : MethodService {

    @Transactional
    override fun addMethod(methodInfo: MethodInfo) {
        val article = articleService.addArticle(methodInfo.getArticle())
        val section = sectionService.getSection(methodInfo.getSectionId())
        val method = Method(methodInfo.getId(), methodInfo.getName(), section, article)
        methodRepository.save(method)
    }

    override fun deleteMethod(id: Long) {
        existByID(id)
        methodRepository.deleteById(id)
    }

    override fun getMethod(id: Long): Method {
        existByID(id)
        return methodRepository.getOne(id)
    }

    private fun existByID(id: Long) {
        if (!methodRepository.existsById(id)) {
            throw MethodNotFoundException("Check if the id is correct: $id")
        }
    }
}
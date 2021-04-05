package com.jetbrains.life_science.method.service

import com.jetbrains.life_science.article.entity.ArticleInfoImpl
import com.jetbrains.life_science.article.service.ArticleService
import com.jetbrains.life_science.exceptions.MethodNotFoundException
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.entity.MethodInfo
import com.jetbrains.life_science.method.factory.MethodFactory
import com.jetbrains.life_science.method.repository.MethodRepository
import com.jetbrains.life_science.section.service.SectionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MethodServiceImpl(
    val methodRepository: MethodRepository,
    val sectionService: SectionService,
    val methodFactory: MethodFactory,
    val articleService: ArticleService
) : MethodService {

    @Transactional
    override fun addMethod(methodInfo: MethodInfo) {
        val section = sectionService.getSection(methodInfo.getSectionId())
        val method = methodFactory.createMethod(methodInfo, section)
        methodRepository.save(method)
        val containerId = method.generalInfo.id
        articleService.create(ArticleInfoImpl(containerId))
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

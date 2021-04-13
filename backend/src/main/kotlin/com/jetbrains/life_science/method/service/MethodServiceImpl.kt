package com.jetbrains.life_science.method.service

import com.jetbrains.life_science.exceptions.MethodNotFoundException
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.factory.MethodFactory
import com.jetbrains.life_science.method.repository.MethodRepository
import com.jetbrains.life_science.section.service.SectionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MethodServiceImpl(
    val factory: MethodFactory,
    val sectionService: SectionService,
    val repository: MethodRepository
) : MethodService {

    @Transactional
    override fun create(info: MethodInfo) {
        val section = sectionService.getSection(info.sectionId)
        var method = factory.create(section)
        method = repository.save(method)
    }

    override fun getById(id: Long): Method {
        return repository.findById(id).orElseThrow { MethodNotFoundException(id) }
    }

    override fun getBySectionId(sectionId: Long): List<Method> {
        return repository.findAllBySectionId(sectionId)
    }
}

package com.jetbrains.life_science.method.service

import com.jetbrains.life_science.container.service.ContainerService
import com.jetbrains.life_science.exceptions.MethodNotFoundException
import com.jetbrains.life_science.method.entity.Method
import com.jetbrains.life_science.method.factory.MethodFactory
import com.jetbrains.life_science.method.repository.MethodRepository
import com.jetbrains.life_science.method.search.service.MethodSearchUnitService
import com.jetbrains.life_science.section.service.SectionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MethodServiceImpl(
    val repository: MethodRepository,
    val methodFactory: MethodFactory,
    val searchUnitService: MethodSearchUnitService,
) : MethodService {

    @Autowired
    lateinit var sectionService: SectionService

    @Autowired
    lateinit var containerService: ContainerService

    @Transactional
    override fun create(methodInfo: MethodInfo): Method {
        val section = sectionService.getSection(methodInfo.sectionId)
        var method = methodFactory.createMethod(methodInfo, section)
        // Creating method in database
        method = repository.save(method)
        // Completing container and creating empty article
        containerService.completeCreationGeneralInfo(method.generalInfo)
        // Creating document
        searchUnitService.create(method)
        return method
    }

    @Transactional
    override fun deleteByID(id: Long) {
        val method = getMethod(id)
        method.containers.forEach { containerService.initDeletion(it) }
        repository.delete(method)
        // Deleting from elastic
        searchUnitService.delete(id)
    }

    override fun getMethod(id: Long): Method {
        checkExistsById(id)
        return repository.getOne(id)
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw MethodNotFoundException("Check if the id is correct: $id")
        }
    }
}

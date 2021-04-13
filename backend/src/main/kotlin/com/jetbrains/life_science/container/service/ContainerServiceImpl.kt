package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.article.service.ArticleService
import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.container.factory.ContainerFactory
import com.jetbrains.life_science.container.repository.ContainerRepository
import com.jetbrains.life_science.container.search.service.ContainerSearchUnitService
import com.jetbrains.life_science.exception.ContainerNotFoundException
import com.jetbrains.life_science.version.entity.MethodVersion
import com.jetbrains.life_science.version.service.MethodVersionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContainerServiceImpl(
    val factory: ContainerFactory,
    val repository: ContainerRepository,
    val searchService: ContainerSearchUnitService
) : ContainerService {

    @Autowired
    lateinit var methodVersionService: MethodVersionService

    @Autowired
    lateinit var articleService: ArticleService

    @Transactional
    override fun create(info: ContainerInfo): Container {
        val method = methodVersionService.getVersionById(info.methodId)
        var container = factory.create(info.name, info.description, method)
        // Creating row in database
        container = repository.save(container)
        return container
    }

    @Transactional
    override fun deleteById(id: Long) {
        checkExistsById(id)
        val container = repository.findById(id).orElseThrow()
        articleService.deleteByContainerId(id)
        // Deleting row in database
        repository.delete(container)
    }

    @Transactional
    override fun createCopiesByMethod(method: MethodVersion, newMethod: MethodVersion) {
        val containers = repository.findAllByMethod(method)
        containers.forEach { container -> createCopy(container, newMethod) }
    }

    override fun deleteSearchUnits(oldContainers: List<Container>) {
        oldContainers.forEach { searchService.delete(it.id) }
    }

    override fun createSearchUnits(newContainers: List<Container>) {
        newContainers.forEach { searchService.create(it) }
    }

    private fun createCopy(origin: Container, newMethod: MethodVersion) {
        val copy = factory.copy(origin)
        copy.method = newMethod
        articleService.createCopiesByContainer(origin, copy)
        repository.save(copy)
    }

    override fun createBlankByVersion(info: ContainerCreationInfo): Container {
        val container = factory.createByVersion(info)
        return repository.save(container)
    }

    override fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw ContainerNotFoundException("Container not found by id: $id")
        }
    }

    @Transactional
    override fun update(into: ContainerUpdateInfo) {
        val container = getById(into.id)
        factory.setParams(container, into)
    }

    override fun getById(id: Long): Container {
        return repository.findById(id)
            .orElseThrow { throw ContainerNotFoundException("Container not found by id: $id") }
    }
}

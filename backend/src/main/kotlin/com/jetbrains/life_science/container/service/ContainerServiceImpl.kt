package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.article.service.ArticleService
import com.jetbrains.life_science.article.service.impl.ArticleEmptyCreationToInfoAdapter
import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.container.factory.ContainerFactory
import com.jetbrains.life_science.container.repository.ContainerRepository
import com.jetbrains.life_science.container.search.service.ContainerSearchUnitService
import com.jetbrains.life_science.exceptions.ContainerNotFoundException
import com.jetbrains.life_science.exceptions.GeneralInformationDeletionException
import com.jetbrains.life_science.method.service.MethodService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContainerServiceImpl(
    val factory: ContainerFactory,
    val repository: ContainerRepository,
    val searchUnitService: ContainerSearchUnitService
) : ContainerService {

    @Autowired
    lateinit var methodService: MethodService

    @Autowired
    lateinit var articleService: ArticleService

    @Transactional
    override fun create(info: ContainerInfo): Container {
        val method = methodService.getMethod(info.methodId)
        var container = factory.create(info.name, info.description, method)
        // Creating row in database
        container = repository.save(container)
        // Creating document in elastic
        searchUnitService.create(container)
        return container
    }

    @Transactional
    override fun deleteById(id: Long) {
        checkExistsById(id)
        val container = repository.findById(id).orElseThrow()
        checkNotGeneralInfo(container)
        articleService.deleteByContainerId(id)
        // Deleting row in database
        repository.delete(container)
        // Deleting document in elastic
        searchUnitService.delete(container.id)
    }

    /**
     * Cleans the contents of the container before removing it
     */
    override fun initDeletion(container: Container) {
        checkExistsById(container.id)
        searchUnitService.delete(container.id)
        articleService.deleteByContainerId(container.id)
    }

    private fun checkNotGeneralInfo(container: Container) {
        if (container.method.generalInfo.id == container.id) {
            throw GeneralInformationDeletionException("Attempt to delete general information of method: ${container.method.id}")
        }
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
        // Updating document in elastic
        searchUnitService.update(container)
    }

    /**
     * Creates a search unit for container and an empty article, after it was stored id database
     */
    override fun completeCreationGeneralInfo(container: Container) {
        checkExistsById(container.id)
        // Creating document in elastic
        searchUnitService.create(container)
        // Creating an empty article
        articleService.create(ArticleEmptyCreationToInfoAdapter(container.id))
    }

    private fun getById(id: Long): Container {
        return repository.findById(id)
            .orElseThrow { throw ContainerNotFoundException("Container not found by id: $id") }
    }
}

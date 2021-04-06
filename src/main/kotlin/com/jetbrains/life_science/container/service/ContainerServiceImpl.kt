package com.jetbrains.life_science.container.service

import com.jetbrains.life_science.article.service.ArticleService
import com.jetbrains.life_science.container.entity.Container
import com.jetbrains.life_science.container.entity.ContainerInfo
import com.jetbrains.life_science.container.factory.ContainerFactory
import com.jetbrains.life_science.container.repository.ContainerRepository
import com.jetbrains.life_science.exceptions.ContainerNotFoundException
import com.jetbrains.life_science.exceptions.GeneralInformationDeletionException
import com.jetbrains.life_science.method.service.MethodService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContainerServiceImpl(
    val containerFactory: ContainerFactory,
    val repository: ContainerRepository,
    val articleService: ArticleService
) : ContainerService {

    @Autowired
    lateinit var methodService: MethodService

    override fun create(info: ContainerInfo): Container {
        val method = methodService.getMethod(info.methodId)
        val container = containerFactory.create(info.name, info.description, method)
        return repository.save(container)
    }

    @Transactional
    override fun deleteById(id: Long) {
        checkExistsById(id)
        val container = repository.findById(id).orElseThrow()
        checkNotGeneralInfo(container)
        articleService.deleteByContainerId(id)
        repository.delete(container)
    }

    override fun prepareDeletionById(containerId: Long) {
        checkExistsById(containerId)
        articleService.deleteByContainerId(containerId)
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
}

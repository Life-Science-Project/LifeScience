package com.jetbrains.life_science.version.service

import com.jetbrains.life_science.container.service.ContainerService
import com.jetbrains.life_science.exceptions.MethodVersionNotFoundException
import com.jetbrains.life_science.exceptions.PublishedVersionNotFoundException
import com.jetbrains.life_science.method.service.MethodService
import com.jetbrains.life_science.version.adapters.ContainerEmptyCreationToInfoAdapter
import com.jetbrains.life_science.version.entity.MethodVersion
import com.jetbrains.life_science.version.entity.State
import com.jetbrains.life_science.version.factory.MethodVersionFactory
import com.jetbrains.life_science.version.repository.MethodVersionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MethodVersionServiceImpl(
    val repository: MethodVersionRepository,
    val factory: MethodVersionFactory,
    val methodService: MethodService,
    val containerService: ContainerService
) : MethodVersionService {

    @Transactional
    override fun createBlack(info: MethodVersionInfo): MethodVersion {
        val method = methodService.getById(info.methodId)
        var methodVersion = factory.create(info, method)
        methodVersion = repository.save(methodVersion)
        containerService.createBlankByVersion(ContainerEmptyCreationToInfoAdapter(methodVersion))
        return methodVersion
    }

    @Transactional
    override fun createCopy(methodId: Long) {
        val publishedVersion = getPublishedVersion(methodId)
        var copy = factory.createCopy(publishedVersion)
        copy = repository.save(copy)
        containerService.createCopiesByMethod(publishedVersion, copy)
    }

    @Transactional
    override fun getPublishedVersion(methodId: Long): MethodVersion {
        return (repository.findByMainMethod_IdAndState(methodId, State.PUBLISHED)
            ?: throw PublishedVersionNotFoundException("published version to method $methodId not found"))
    }

    @Transactional
    override fun approve(id: Long) {
        val version = getVersionById(id)
        version.state = State.PUBLISHED
        val lastPublished = repository.findByMainMethod_IdAndState(version.mainMethod.id, State.PUBLISHED)
        lastPublished?.state = State.ARCHIVED
    }

    @Transactional
    override fun getVersionById(id: Long): MethodVersion {
        return repository.findById(id)
            .orElseThrow { MethodVersionNotFoundException(id) }
    }

    private fun checkExistsById(id: Long) {
        if (repository.existsById(id)) {
            throw MethodVersionNotFoundException(id)
        }
    }

}

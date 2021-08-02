package com.jetbrains.life_science.replicator

import com.fasterxml.jackson.module.kotlin.*
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.container.approach.repository.PublicApproachRepository
import com.jetbrains.life_science.container.protocol.repository.PublicProtocolRepository
import com.jetbrains.life_science.content.publish.repository.ContentRepository
import com.jetbrains.life_science.replicator.category.CategoryReplicator
import com.jetbrains.life_science.replicator.enities.CommonStorageEntity
import com.jetbrains.life_science.section.repository.SectionRepository
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class ReplicatorServiceImpl(
    val categoryReplicator: CategoryReplicator,
    val resourceLoader: ResourceLoader
) : ReplicatorService {

    private val pathToData = "classpath:replica/data.json"

    override fun replicateData() {
        val (categories, approaches) = decodeData()
        categoryReplicator.replicateData(categories)
    }

    private fun decodeData(): CommonStorageEntity {
        val resource = resourceLoader.getResource(pathToData)
        val textContent = resource.inputStream.bufferedReader().readText()
        return jacksonObjectMapper().readValue(textContent)
    }


}
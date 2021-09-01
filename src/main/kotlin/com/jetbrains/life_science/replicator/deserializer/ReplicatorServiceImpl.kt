package com.jetbrains.life_science.replicator.deserializer

import com.fasterxml.jackson.module.kotlin.*
import com.jetbrains.life_science.replicator.deserializer.approach.ApproachReplicator
import com.jetbrains.life_science.replicator.deserializer.category.CategoryReplicator
import com.jetbrains.life_science.replicator.deserializer.content.ContentReplicator
import com.jetbrains.life_science.replicator.deserializer.credentials.CredentialsReplicator
import com.jetbrains.life_science.replicator.enities.CommonStorageEntity
import com.jetbrains.life_science.replicator.deserializer.protocol.ProtocolReplicator
import com.jetbrains.life_science.replicator.deserializer.section.SectionReplicator
import com.jetbrains.life_science.util.getLogger
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class ReplicatorServiceImpl(
    val categoryReplicator: CategoryReplicator,
    val resourceLoader: ResourceLoader,
    val approachReplicator: ApproachReplicator,
    val protocolReplicator: ProtocolReplicator,
    val sectionReplicator: SectionReplicator,
    val contentReplicator: ContentReplicator,
    val credentialsReplicator: CredentialsReplicator
) : ReplicatorService {

    private val pathToData = "classpath:replica/data.json"

    private val logger = getLogger()

    override fun replicateData() {
        logger.info("Replication started")
        deleteAll()
        logger.info("Deletion success")
        credentialsReplicator.createAdmin()
        val (categories, approaches) = decodeData()
        categoryReplicator.replicateData(categories)
        approachReplicator.replicateData(approaches)
        logger.info("Replication success")
    }

    private fun deleteAll() {
        contentReplicator.deleteAll()
        protocolReplicator.deleteAll()
        approachReplicator.deleteAll()
        sectionReplicator.deleteAll()
        categoryReplicator.deleteAll()
    }

    private fun decodeData(): CommonStorageEntity {
        val resource = resourceLoader.getResource(pathToData)
        val textContent = resource.inputStream.bufferedReader().readText()
        return jacksonObjectMapper().readValue(textContent)
    }
}

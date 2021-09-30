package com.jetbrains.life_science.replicator.deserializer

import com.fasterxml.jackson.module.kotlin.*
import com.jetbrains.life_science.replicator.deserializer.approach.ApproachReplicator
import com.jetbrains.life_science.replicator.deserializer.category.CategoryReplicator
import com.jetbrains.life_science.replicator.deserializer.content.ContentReplicator
import com.jetbrains.life_science.replicator.deserializer.credentials.CredentialsReplicator
import com.jetbrains.life_science.replicator.deserializer.favoriteGroup.FavoriteGroupReplicator
import com.jetbrains.life_science.replicator.enities.CommonStorageEntity
import com.jetbrains.life_science.replicator.deserializer.protocol.ProtocolReplicator
import com.jetbrains.life_science.replicator.deserializer.section.SectionReplicator
import com.jetbrains.life_science.util.getLogger
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service

@Service
class ReplicatorServiceImpl(
    private val categoryReplicator: CategoryReplicator,
    private val resourceLoader: ResourceLoader,
    private val approachReplicator: ApproachReplicator,
    private val protocolReplicator: ProtocolReplicator,
    private val sectionReplicator: SectionReplicator,
    private val contentReplicator: ContentReplicator,
    private val credentialsReplicator: CredentialsReplicator,
    private val favoriteGroupReplicator: FavoriteGroupReplicator
) : ReplicatorService {

    private val pathToData = "classpath:replica/data.json"

    private val logger = getLogger()

    override fun replicateData() {
        logger.info("Replication started")
        deleteAll()
        logger.info("Deletion success")
        val (users, categories, publicApproaches, draftApproaches, draftProtocols) = decodeData()
        credentialsReplicator.replicateData(users)
        categoryReplicator.replicateData(categories)
        approachReplicator.replicateData(publicApproaches, draftApproaches)
        protocolReplicator.replicateDraftProtocolData(draftProtocols)
        logger.info("Replication success")
    }

    private fun deleteAll() {
        contentReplicator.deleteAll()
        protocolReplicator.deleteAll()
        approachReplicator.deleteAllDraft()
        categoryReplicator.deleteAll()
        approachReplicator.deleteAllPublic()
        sectionReplicator.deleteAll()
        credentialsReplicator.deleteAll()
        favoriteGroupReplicator.deleteAll()
    }

    private fun decodeData(): CommonStorageEntity {
        val resource = resourceLoader.getResource(pathToData)
        val textContent = resource.inputStream.bufferedReader().readText()
        return jacksonObjectMapper().readValue(textContent)
    }
}

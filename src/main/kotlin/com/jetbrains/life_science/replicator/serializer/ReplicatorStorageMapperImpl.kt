package com.jetbrains.life_science.replicator.serializer

import com.jetbrains.life_science.replicator.enities.CommonStorageEntity
import com.jetbrains.life_science.replicator.serializer.approach.ApproachToStorageEntityMapper
import com.jetbrains.life_science.replicator.serializer.category.CategoryToStorageEntityMapper
import com.jetbrains.life_science.replicator.serializer.protocol.ProtocolToStorageEntityMapper
import com.jetbrains.life_science.replicator.serializer.user.CredentialsToStorageEntityMapper
import org.springframework.stereotype.Service

@Service
class ReplicatorStorageMapperImpl(
    private val categoryToStorageEntityMapper: CategoryToStorageEntityMapper,
    private val approachToStorageEntityMapper: ApproachToStorageEntityMapper,
    private val credentialsToStorageEntityMapper: CredentialsToStorageEntityMapper,
    private val protocolToStorageEntityMapper: ProtocolToStorageEntityMapper
) : ReplicatorStorageMapper {

    override fun serialize(): CommonStorageEntity {
        val users = credentialsToStorageEntityMapper.getStorageEntities()
        val (categories, idsMap) = categoryToStorageEntityMapper.getStorageEntities()
        val publicApproaches = approachToStorageEntityMapper.getPublicApproachStorageEntities(idsMap)
        val draftApproaches = approachToStorageEntityMapper.getDraftApproachStorageEntities(idsMap)
        val draftProtocols = protocolToStorageEntityMapper.getDraftStorageEntities()
        return CommonStorageEntity(users, categories, publicApproaches, draftApproaches, draftProtocols)
    }
}

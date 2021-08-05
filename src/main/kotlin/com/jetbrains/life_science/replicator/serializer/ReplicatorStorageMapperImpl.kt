package com.jetbrains.life_science.replicator.serializer

import com.jetbrains.life_science.replicator.enities.CommonStorageEntity
import com.jetbrains.life_science.replicator.serializer.approach.ApproachToStorageEntityMapper
import com.jetbrains.life_science.replicator.serializer.category.CategoryToStorageEntityMapper
import org.springframework.stereotype.Service

@Service
class ReplicatorStorageMapperImpl(
    val categoryToStorageEntityMapper: CategoryToStorageEntityMapper,
    val approachToStorageEntityMapper: ApproachToStorageEntityMapper
) : ReplicatorStorageMapper {

    override fun serialize(): CommonStorageEntity {
        val (categories, idsMap) = categoryToStorageEntityMapper.getStorageEntities()
        val approaches = approachToStorageEntityMapper.getStorageEntities(idsMap)
        return CommonStorageEntity(categories, approaches)
    }
}

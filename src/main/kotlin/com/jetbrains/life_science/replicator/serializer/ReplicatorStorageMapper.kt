package com.jetbrains.life_science.replicator.serializer

import com.jetbrains.life_science.replicator.enities.CommonStorageEntity

interface ReplicatorStorageMapper {

    fun serialize(): CommonStorageEntity
}

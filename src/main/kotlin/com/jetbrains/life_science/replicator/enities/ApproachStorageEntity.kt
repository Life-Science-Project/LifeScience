package com.jetbrains.life_science.replicator.enities

data class ApproachStorageEntity(
    val name: String,
    val aliases: MutableList<String>,
    val categories: List<Long>,
    val sections: List<SectionStorageEntity>,
    val protocols: List<ProtocolStorageEntity>,
    val creationDateTime: String,
    val ownerId: Long
)

data class ProtocolStorageEntity(
    val name: String,
    val ownerId: Long,
    val rating: Long?,
    val sections: List<SectionStorageEntity>,
    val approachId: Long
)

data class SectionStorageEntity(
    val name: String,
    val content: String?,
    val hidden: Boolean
)

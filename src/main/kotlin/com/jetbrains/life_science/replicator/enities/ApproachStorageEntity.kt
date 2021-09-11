package com.jetbrains.life_science.replicator.enities

import java.time.LocalDateTime

data class ApproachStorageEntity(
    val name: String,
    val aliases: MutableList<String>,
    val categories: List<Long>,
    val sections: List<SectionStorageEntity>,
    val protocols: List<ProtocolStorageEntity>,
    val creationDateTime: LocalDateTime,
    val ownerId: Long
)

data class ProtocolStorageEntity(
    val name: String,
    val sections: List<SectionStorageEntity>
)

data class SectionStorageEntity(
    val name: String,
    val content: String?,
    val hidden: Boolean
)

package com.jetbrains.life_science.replicator.enities

data class ApproachStorageEntity(
    val name: String,
    val aliases: List<String>,
    val categories: List<Long>,
    val sections: List<SectionStorageEntity>,
    val protocols: List<ProtocolStorageEntity>
)

data class ProtocolStorageEntity(
    val name: String,
    val sections: List<SectionStorageEntity>
)


data class SectionStorageEntity(
    val name: String,
    val content: String
)


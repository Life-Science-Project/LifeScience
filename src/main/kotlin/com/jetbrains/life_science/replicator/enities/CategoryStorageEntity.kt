package com.jetbrains.life_science.replicator.enities

data class CategoryStorageEntity(
    val id: Long,
    val name: String,
    val aliases: List<String>,
    val parents: List<Long>
)

package com.jetbrains.life_science.replicator.enities

data class CategoryStorageEntity(
    var id: Long,
    val name: String,
    val aliases: List<String>,
    var parents: List<Long>
)

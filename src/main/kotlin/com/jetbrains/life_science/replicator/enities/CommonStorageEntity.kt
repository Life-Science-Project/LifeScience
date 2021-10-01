package com.jetbrains.life_science.replicator.enities

data class CommonStorageEntity(
    val category: List<CategoryStorageEntity>,
    val approach: List<ApproachStorageEntity>
)

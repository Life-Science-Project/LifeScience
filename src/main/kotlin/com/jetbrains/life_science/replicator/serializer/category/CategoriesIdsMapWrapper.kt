package com.jetbrains.life_science.replicator.serializer.category

import com.jetbrains.life_science.replicator.enities.CategoryStorageEntity

data class CategoriesIdsMapWrapper(
    val categories: List<CategoryStorageEntity>,
    val idsMap: Map<Long, Long>
)

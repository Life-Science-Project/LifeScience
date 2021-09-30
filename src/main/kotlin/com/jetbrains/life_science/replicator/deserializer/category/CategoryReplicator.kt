package com.jetbrains.life_science.replicator.deserializer.category

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.repository.CategoryRepository
import com.jetbrains.life_science.category.search.repository.CategorySearchUnitRepository
import com.jetbrains.life_science.category.search.service.CategorySearchUnitService
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.replicator.enities.CategoryStorageEntity
import com.jetbrains.life_science.util.ElasticFlusher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import javax.persistence.EntityManager

@Component
class CategoryReplicator(
    private val categoryRepository: CategoryRepository,
    private val categoryService: CategoryService,
    private val categorySearchUnitService: CategorySearchUnitService,
    private val categorySearchUnitRepository: CategorySearchUnitRepository,
    private val entityManager: EntityManager,
    private val elasticFlusher: ElasticFlusher
) {

    @Transactional
    fun deleteAll() {
        entityManager.createNativeQuery("alter sequence category_seq restart with 1;")
            .executeUpdate()
        categoryRepository.deleteAll()
        categorySearchUnitRepository.deleteAll()
    }

    @Transactional
    fun replicateData(data: List<CategoryStorageEntity>) {
        categorySearchUnitRepository.deleteAll()
        val sortedData = data.sortedWith(compareBy { it.id })
        sortedData.forEach { createOne(it) }
    }

    private fun createOne(storageEntity: CategoryStorageEntity) {
        var category = createCategory(storageEntity)
        category = categoryRepository.save(category)
        categoryRepository.flush()
        categorySearchUnitService.createSearchUnit(category)
        elasticFlusher.flush(100)
        entityManager.flush()
    }

    private fun createCategory(storageEntity: CategoryStorageEntity) =
        Category(
            id = storageEntity.id,
            name = storageEntity.name,
            aliases = storageEntity.aliases,
            subCategories = mutableListOf(),
            parents = storageEntity.parents.map { categoryService.getById(it) }.toMutableList(),
            approaches = mutableListOf(),
            creationDate = getCreationDate()
        )

    private fun getCreationDate() = LocalDateTime.of(2021, 6, 1, 0, 0)
}

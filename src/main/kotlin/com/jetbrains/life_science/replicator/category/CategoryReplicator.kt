package com.jetbrains.life_science.replicator.category

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.repository.CategoryRepository
import com.jetbrains.life_science.category.search.repository.CategorySearchUnitRepository
import com.jetbrains.life_science.category.search.service.CategorySearchUnitService
import com.jetbrains.life_science.replicator.enities.CategoryStorageEntity
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import javax.persistence.EntityManager

@Component
class CategoryReplicator(
    val categoryRepository: CategoryRepository,
    val categorySearchUnitService: CategorySearchUnitService,
    val categorySearchUnitRepository: CategorySearchUnitRepository,
    val entityManager: EntityManager
) {

    @Transactional
    fun deleteAll() {
        entityManager.createNativeQuery("ALTER SEQUENCE category_seq RESTART WITH 1;")
            .executeUpdate()
        categoryRepository.deleteAll()
        categorySearchUnitRepository.deleteAll()
    }

    @Transactional
    fun replicateData(data: List<CategoryStorageEntity>) {
        categorySearchUnitRepository.deleteAll()
        data.forEach { createOne(it) }
    }

    fun createOne(storageEntity: CategoryStorageEntity) {
        var category = createCategory(storageEntity)
        category = categoryRepository.save(category)
        categorySearchUnitService.createSearchUnit(category)
    }

    private fun createCategory(storageEntity: CategoryStorageEntity) =
        Category(
            id = storageEntity.id,
            name = storageEntity.name,
            aliases = storageEntity.aliases,
            subCategories = mutableListOf(),
            parents = storageEntity.parents.map { categoryRepository.findCategoryById(it)!! }.toMutableList(),
            approaches = mutableListOf(),
            creationDate = getCreationDate()
        )

    private fun getCreationDate() = LocalDateTime.of(2021, 6, 1, 0, 0)

}
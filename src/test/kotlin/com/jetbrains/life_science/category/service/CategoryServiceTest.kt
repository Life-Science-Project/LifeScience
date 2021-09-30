package com.jetbrains.life_science.category.service

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.service.maker.makeCategoryInfo
import com.jetbrains.life_science.category.service.maker.makeCategoryUpdateInfo
import com.jetbrains.life_science.exception.category.CategoryNoParentsException
import com.jetbrains.life_science.exception.category.CategoryNotEmptyException
import com.jetbrains.life_science.exception.category.CategoryNotFoundException
import com.jetbrains.life_science.exception.category.CategoryParentNotFoundException
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@SpringBootTest
@Sql("/scripts/initial_data.sql")
@Transactional
class CategoryServiceTest {

    @Autowired
    lateinit var service: CategoryService

    lateinit var elasticPopulator: ElasticPopulator

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient).apply {
            addPopulator("category", "elastic/category.json")
        }
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    @Test
    fun `root categories get test`() {
        // Prepare
        val expectedCategories = listOf(
            Category(
                id = 1,
                name = "catalog 1",
                aliases = emptyList(),
                subCategories = mutableListOf(
                    // too many dependencies
                ),
                approaches = mutableListOf(
                    // too many dependencies
                ),
                parents = mutableListOf(
                    // too many dependencies
                ),
                creationDate = LocalDateTime.of(2020, 9, 17, 0, 0, 0)
            ),
            Category(
                id = 2,
                name = "catalog 2",
                aliases = emptyList(),
                subCategories = mutableListOf(
                    // too many dependencies
                ),
                approaches = mutableListOf(
                    // too many dependencies
                ),
                parents = mutableListOf(
                    // too many dependencies
                ),
                creationDate = LocalDateTime.of(2020, 10, 17, 0, 0, 0)
            )
        )

        // Action
        val rootCategories = service.getRootCategories()

        // Assert
        assertEquals(expectedCategories.size, rootCategories.size)
        rootCategories.forEachIndexed { index, category ->
            assertEquals(expectedCategories[index].id, category.id)
            assertEquals(expectedCategories[index].name, category.name)
            assertEquals(expectedCategories[index].creationDate, category.creationDate)
        }
    }

    @Test
    fun `get existing category`() {
        // Prepare
        val expectedCategory = Category(
            id = 1,
            name = "catalog 1",
            aliases = emptyList(),
            subCategories = mutableListOf(
                // too many dependencies
            ),
            approaches = mutableListOf(
                // too many dependencies
            ),
            parents = mutableListOf(
                // too many dependencies
            ),
            creationDate = LocalDateTime.of(2020, 9, 17, 0, 0, 0)
        )

        // Action
        val category = service.getById(1)

        // Assert
        assertEquals(expectedCategory.id, category.id)
        assertEquals(expectedCategory.name, category.name)
        assertEquals(expectedCategory.creationDate, category.creationDate)
    }

    @Test
    fun `create category`() {
        // Prepare
        val info = makeCategoryInfo(
            name = "my category", parentId = 3,
            aliases = listOf(
                "second name",
                "third name"
            )
        )

        // Action
        val createdCategory = service.createCategory(info)
        val category = service.getById(createdCategory.id)

        // Assert
        assertEquals(createdCategory.id, category.id)
        assertEquals(info.name, category.name)
        assertEquals(info.aliases, category.aliases)
        assertEquals(info.parentId, category.parents[0].id)
    }

    @Test
    fun `update category test`() {
        // Prepare
        val info = makeCategoryUpdateInfo(
            id = 5,
            name = "changed name",
            aliases = listOf(
                "le name"
            ),
            parentsToAddIds = listOf(3),
            parentsToDeleteIds = listOf(2)
        )

        // Action
        service.updateCategory(info)
        val category = service.getById(5)

        // Assert
        assertEquals(info.id, category.id)
        assertEquals(info.name, category.name)
        assertEquals(info.aliases, category.aliases)
        assertTrue(category.parents.any { it.id == 3L })
        assertFalse(category.parents.any { it.id == 2L })
    }

    /**
     * Try to update non-existing category.
     * It should throw CategoryNotEmptyException.
     */
    @Test
    fun `update non-empty category test`() {
        // Prepare
        val info = makeCategoryUpdateInfo(
            id = 1,
            name = "changed name",
            aliases = listOf(
                "le name"
            ),
            parentsToAddIds = listOf(),
            parentsToDeleteIds = listOf()
        )

        // Action & Assert
        assertThrows<CategoryNotEmptyException> {
            service.updateCategory(info)
        }
    }

    @Test
    fun `delete existing empty category`() {
        // Prepare
        val existingId = 5L

        // Action
        service.deleteCategory(existingId)

        // Assert
        assertThrows<CategoryNotFoundException> {
            service.getById(existingId)
        }
    }

    @Test
    fun `delete existing not empty category`() {
        // Prepare
        val existingId = 1L

        // Action & Assert
        assertThrows<CategoryNotEmptyException> {
            service.deleteCategory(existingId)
        }
    }

    @Test
    fun `get not existing category`() {
        // Prepare
        val notExistingId = 666L

        // Action & Assert
        assertThrows<CategoryNotFoundException> {
            service.getById(notExistingId)
        }
    }

    @Test
    fun `delete not existing category`() {
        // Prepare
        val notExistingId = 666L

        // Action & Assert
        assertThrows<CategoryNotFoundException> {
            service.deleteCategory(notExistingId)
        }
    }

    @Test
    fun `create category with not existing parent`() {
        // Prepare
        val info = makeCategoryInfo(
            name = "my category", parentId = 666,
            aliases = listOf(
                "second name",
                "third name"
            )
        )

        // Action & Assert
        assertThrows<CategoryParentNotFoundException> {
            service.createCategory(info)
        }
    }

    @Test
    fun `remove all parents test`() {
        // Prepare
        val info = makeCategoryUpdateInfo(
            id = 5,
            name = "changed name",
            aliases = listOf(
                "le name"
            ),
            parentsToAddIds = listOf(),
            parentsToDeleteIds = listOf(2, 4)
        )

        // Action & Assert
        assertThrows<CategoryNoParentsException> {
            service.updateCategory(info)
        }
    }

    @Test
    fun `add not existing parent test`() {
        // Prepare
        val info = makeCategoryUpdateInfo(
            id = 5,
            name = "changed name",
            aliases = listOf(
                "le name"
            ),
            parentsToAddIds = listOf(666),
            parentsToDeleteIds = listOf()
        )

        // Action & Assert
        assertThrows<CategoryParentNotFoundException> {
            service.updateCategory(info)
        }
    }

    @Test
    fun `parent to delete not found test`() {
        // Prepare
        val info = makeCategoryUpdateInfo(
            id = 5,
            name = "changed name",
            aliases = listOf(
                "le name"
            ),
            parentsToAddIds = listOf(),
            parentsToDeleteIds = listOf(666)
        )

        // Action & Assert
        assertThrows<CategoryParentNotFoundException> {
            service.updateCategory(info)
        }
    }
}

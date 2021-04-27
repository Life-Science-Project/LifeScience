package com.jetbrains.life_science.category.repository

import com.jetbrains.life_science.category.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {

    fun findAllByParentIsNull(): List<Category>
}

package com.jetbrains.life_science.article.section.parameters.repository

import com.jetbrains.life_science.article.section.parameters.entity.Parameters
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ParametersRepository : JpaRepository<Parameters, Long>

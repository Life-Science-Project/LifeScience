package com.jetbrains.life_science.article.section.parameter.repository

import com.jetbrains.life_science.article.section.parameter.entity.Parameter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ParameterRepository : JpaRepository<Parameter, Long>

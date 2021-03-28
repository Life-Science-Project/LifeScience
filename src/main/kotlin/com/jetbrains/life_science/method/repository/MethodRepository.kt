package com.jetbrains.life_science.method.repository

import com.jetbrains.life_science.method.entity.Method
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MethodRepository : JpaRepository<Method, Long>

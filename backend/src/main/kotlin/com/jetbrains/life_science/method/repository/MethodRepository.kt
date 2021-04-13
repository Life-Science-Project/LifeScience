package com.jetbrains.life_science.method.repository

import com.jetbrains.life_science.method.entity.Method
import org.springframework.data.jpa.repository.JpaRepository

interface MethodRepository: JpaRepository<Method, Long>

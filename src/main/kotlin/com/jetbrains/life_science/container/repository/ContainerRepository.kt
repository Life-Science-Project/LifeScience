package com.jetbrains.life_science.container.repository

import com.jetbrains.life_science.container.entity.Container
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContainerRepository: JpaRepository<Container, Long>
